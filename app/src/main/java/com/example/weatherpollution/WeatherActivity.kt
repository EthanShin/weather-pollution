package com.example.weatherpollution

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.example.weatherpollution.Data.Item
import com.example.weatherpollution.Data.LocationData
import com.example.weatherpollution.Data.MsrstnData
import com.example.weatherpollution.Data.WeatherData
import kotlinx.android.synthetic.main.activity_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.*

class WeatherActivity : AppCompatActivity(), LocationListener {

    private val PERMISSION_REQUEST_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        getLocationInfo()
    }

    // GPS를 이용하여 위도, 경도 획득
    private fun getLocationInfo() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                this@WeatherActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this@WeatherActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        } else {
            val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude

                Log.d("test_location", "1 lat = $latitude, lon = $longitude")
                changeLocationTypeToXY(latitude, longitude)
                changeLocationTypeToTM(latitude, longitude)
            } else {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,     // 수행대기시간
                    0F,
                    this
                )
                locationManager.removeUpdates(this)
            }
        }
    }

    // Location Permission 요청 결과
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) getLocationInfo()
        }
    }

    override fun onLocationChanged(location: Location?) {
        val latitude = location?.latitude
        val longitude = location?.longitude

        Log.d("test_location", "2 lat = $latitude, lon = $longitude")
        if (latitude != null && longitude != null) {
            changeLocationTypeToXY(latitude, longitude)
            changeLocationTypeToTM(latitude, longitude)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

    private fun changeLocationTypeToTM(lat: Double, lon: Double) {
        (application as WeatherApplication)
            .requestService(1)
            ?.getTMlocation(
                x = lon,
                y = lat,
                outputCoord = "TM"
            )
            ?.enqueue(object: Callback<LocationData> {
                override fun onFailure(call: Call<LocationData>, t: Throwable) {
                    Log.d("test_request", "response_fail: $t")
                }

                override fun onResponse(call: Call<LocationData>, response: Response<LocationData>) {
                    var locationData = response.body() // Parser 사용
                    var documents = locationData?.documents
                    Log.d("test_request", "tm body: " + documents?.get(0)?.x)
                    Log.d("test_request", "tm body: " + documents?.get(0)?.y)

                    if (documents != null) {
                        requestMsrstnList(documents?.get(0)?.x.toString(), documents?.get(0)?.y.toString())
                    }
                }
            })
    }

    // 가장 가까운 미세먼지 측정소의 목록을 요청하는 함수
    private fun requestMsrstnList(x: String, y: String) {
        (application as WeatherApplication)
            .requestService(2)
            ?.getMsrstnList(
                serviceKey = BuildConfig.SERVICE_KEY,
                x = x,
                y = y,
                returnType = "json"
            )
            ?.enqueue(object: Callback<MsrstnData> {
                override fun onFailure(call: Call<MsrstnData>, t: Throwable) {
                    Log.d("test_request", "response_fail: $t")
                }

                override fun onResponse(call: Call<MsrstnData>, response: Response<MsrstnData>) {
                    var msrstnData = response.body() // Parser 사용
                    var list = msrstnData?.list
                    Log.d("test_request", "ms body: " + list?.get(0)?.stationName)
                }
            })
    }

    // 위도, 경도 값을 API 서버가 요구하는 X, Y 좌표로 변경
    private fun changeLocationTypeToXY(lat: Double, lon: Double) {
        val Re = 6371.00877
        val grid = 5.0
        val Slat1 = 30.0
        val Slat2 = 60.0
        val Olon = 126.0
        val Olat = 38.0
        val xo = 210 / grid
        val yo = 675 / grid

        val PI = asin(1.0) * 2.0
        val DEGRAD = PI / 180.0
        val re = Re / grid
        val slat1 = Slat1 * DEGRAD
        val slat2 = Slat2 * DEGRAD
        val olon = Olon * DEGRAD
        val olat = Olat * DEGRAD

        var sn = tan(PI * 0.25 + slat2 * 0.5) / tan(PI * 0.25 + slat1 * 0.5)
        sn = log10(cos(slat1) / cos(slat2)) / log10(sn)
        var sf = tan(PI * 0.25 + slat1 * 0.5)
        sf = sf.pow(sn) * cos(slat1) / sn
        var ro = tan(PI * 0.25 + olat * 0.5)
        ro = re * sf / ro.pow(sn)

        var ra = tan(PI * 0.25 + lat * DEGRAD * 0.5)
        println(ra.pow(sn))
        ra = re * sf / ra.pow(sn)
        var theta = lon * DEGRAD - olon
        if (theta > PI) theta -= 2.0 * PI
        if (theta < -PI) theta += 2.0 * PI
        theta *= sn

        var x = (ra * sin(theta) + xo + 1.5).toInt()
        var y = (ro - ra * cos(theta) + yo + 1.5).toInt()

        Log.d("test_location", "3 x = $x, y = $y")
        requestWeatherInfoOfLocation(x, y)
        request3hoursWeatherInfoOfLocation(x, y)
    }

    // 날짜와 시간을 API 서버가 요구하는 형태로 변경
    data class TimeResult(val date: String, val time: String)
    private fun getBaseTime(date: LocalDateTime, number: Int): TimeResult {

        var checkDate = date
        val h = date.hour
        if (number == 0) {
            val m = date.minute
            if (h % 2 == 1 && m < 20) {
                checkDate = checkDate.withHour(h - 1)
                checkDate = checkDate.withMinute(40)
            }

            return TimeResult(
                checkDate.format(DateTimeFormatter.BASIC_ISO_DATE),
                checkDate.format(DateTimeFormatter.ofPattern("HHmm"))
            )

        } else {
            if (h < 2) { // 공공데이터포털의 데이터가 2시를 기준으로 날짜가 변경됨
                checkDate = checkDate.minusDays(1)  // 2시가 되지 않았다면 전날 23시로 날짜 보정
                checkDate = checkDate.withHour(23)
            } else {
                checkDate = checkDate.withHour(h - (h + 1) % 3) // 3시간 간격으로 기준시간이 변경됨
            }

            return TimeResult(
                checkDate.format(DateTimeFormatter.BASIC_ISO_DATE),
                checkDate.format(DateTimeFormatter.ofPattern("HHmm"))
            )
        }
    }

    // 초단기실황
    private fun requestWeatherInfoOfLocation(x: Int, y: Int) {
        val date = LocalDateTime.now()  // 현재시간
        val (baseDate, baseTime) = getBaseTime(date, 0) // 날짜와 시간분리 ex)20190707, 0500

        Log.d("test_baseTime", "date: $baseDate, time: $baseTime")

        (application as WeatherApplication)
            .requestService(0)
            ?.getWeatherInfoOfLocation(
                serviceKey = BuildConfig.SERVICE_KEY,
                baseDate = baseDate,
                baseTime = baseTime,
                nx = x,
                ny = y,
                numOfRows = 10,
                pageNo = 1,
                type = "json"
            )
            ?.enqueue(object: Callback<WeatherData> {
                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    Log.d("test_request", "1 response_fail: $t")
                }

                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    var weatherData = response.body() // Parser 사용
                    var items = weatherData?.response?.body?.items?.item
                    Log.d("test_request", "1 body: " + items?.get(0)?.baseDate)

                    if (items != null) {
                        drawWeather(items)
                    }
                }
            })

    }

    // 3시간 예보
    private fun request3hoursWeatherInfoOfLocation(x: Int, y: Int) {

        val date = LocalDateTime.now()  // 현재시간
        val (baseDate, baseTime) = getBaseTime(date, 1) // 날짜와 시간분리 ex)20190707, 0500

        Log.d("test_baseTime", "date: $baseDate, time: $baseTime")

        (application as WeatherApplication)
            .requestService(0)
            ?.get3hoursWeatherInfoOfLocation(
                serviceKey = BuildConfig.SERVICE_KEY,
                baseDate = baseDate,
                baseTime = baseTime,
                nx = x,
                ny = y,
                numOfRows = 10,
                pageNo = 1,
                type = "json"
            )
            ?.enqueue(object: Callback<WeatherData> {
                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    Log.d("test_request", "2 response_fail: $t")
                }

                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    var weatherData = response.body() // Parser 사용
                    var items = weatherData?.response?.body?.items?.item
                    Log.d("test_request", "2 body: " + items?.get(0)?.baseDate)

//                    if (items != null) {
//                        drawWeather(items)
//                    }
                }
            })
    }

    // 초단기 실황으로 획득한 값을 화면에 보여줌
    private fun drawWeather(weather: ArrayList<Item>) {
        var sky = "맑음"
        var rain = false
        weather.forEach {
            when(it.category) {
                "PTY" ->
                    when(it.obsrValue) {
                        "0" -> sky = "없음"
                        "1" -> sky = "비"
                        "2" -> sky = "비/눈"
                        "3" -> sky = "눈"
                    }

                "RN1" ->
                    when(it.obsrValue) {
                        "0" -> sky = "맑음"
                        "1" -> rain = true
                    }

                "SKY" ->
                    if(!rain) {
                        when (it.obsrValue) {
                            "0" -> sky = "맑음"
                            "1" -> sky = "구름조금"
                            "2" -> sky = "구름많음"
                            "3" -> sky = "흐림"
                        }
                    }

                "T1H" ->
                    text_temperatures.text = it.obsrValue + " ℃"

                "REH" ->
                    text_humidity.text = it.obsrValue + " %"
            }

            text_weather.text = sky
        }
    }
}
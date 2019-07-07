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
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.*

class WeatherActivity : AppCompatActivity(), LocationListener {

    private val PERMISSION_REQUEST_CODE = 1000
    private val SERVICE_KEY = "Service_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        getLocationInfo()
    }

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
            } else {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    3000L,
                    0F,
                    this
                )
                locationManager.removeUpdates(this)
            }
        }
    }

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
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

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
    }

    private fun requestWeatherInfoOfLocation(x: Int, y: Int) {
        (application as WeatherApplication)
            .requestService()
            ?.getWeatherInfoOfLocation(
                serviceKey = SERVICE_KEY,
                baseDate = "20190708",
                baseTime = "0500",
                nx = x,
                ny = y,
                numOfRows = 10,
                pageNo = 1,
                type = "json"
            )
            ?.enqueue(object: Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("test_request", "response")
                }
            })
    }
}
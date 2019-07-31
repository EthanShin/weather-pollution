package com.example.weatherpollution

import android.annotation.SuppressLint
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import kotlin.math.*

class LocationManager(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationCallback: LocationCallback
) {
    private val locationRequest = LocationRequest().apply {
            interval = 5000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            null /* Looper */)
    }

    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    fun changeLocationType(lat: Double, lon: Double) {
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

        Log.d("test_location", "2 x = $x, y = $y")
    }
}
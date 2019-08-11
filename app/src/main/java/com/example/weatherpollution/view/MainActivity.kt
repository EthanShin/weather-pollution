package com.example.weatherpollution.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.weatherpollution.LocationManager
import com.example.weatherpollution.R
import com.example.weatherpollution.base.BaseActivity
import com.example.weatherpollution.databinding.ActivityMainBinding
import com.example.weatherpollution.viewModel.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val MY_PERMISSION_ACCESS_FINE_LOCATION = 1

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override val viewModel: MainViewModel by viewModel()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            Log.d("TEST", "1: ${p0?.lastLocation?.latitude}, ${p0?.lastLocation?.longitude}")

            if (p0?.lastLocation?.latitude != null && p0.lastLocation?.longitude != null) {
                val(nx, ny) = locationManager.changeLocationType(p0.lastLocation.latitude, p0.lastLocation.longitude)
                viewModel.getWeather(nx, ny)
            }

            locationManager.stopLocationUpdates()
        }
    }

    override fun initStartView() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationManager = LocationManager(fusedLocationProviderClient, locationCallback)

        checkPermissions()
    }

    override fun initDataBinding() {
        viewModel.weatherLiveData.observe(this, Observer {

            textView.text = it.response?.body?.items?.item?.get(0)?.baseDate
        })
    }

    override fun initAfterBinding() {

    }

    @SuppressLint("ObsoleteSdkInt")
    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSION_ACCESS_FINE_LOCATION)
        } else {
            Toast.makeText(this, "Permission has already been granted", Toast.LENGTH_LONG).show()

            getLastLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSION_ACCESS_FINE_LOCATION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permission was granted", Toast.LENGTH_LONG).show()

                getLastLocation()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        locationManager.startLocationUpdates()
    }
}
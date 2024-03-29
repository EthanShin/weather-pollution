package com.example.weatherpollution.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherpollution.LocationManager
import com.example.weatherpollution.R
import com.example.weatherpollution.base.BaseActivity
import com.example.weatherpollution.databinding.ActivityMainBinding
import com.example.weatherpollution.viewModel.MainViewModel
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val MY_PERMISSION_ACCESS_FINE_LOCATION = 1

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override val viewModel: MainViewModel by viewModel()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val locationCallback = object : LocationCallback() {
        @SuppressLint("RestrictedApi")
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            Log.d("TEST", "location: ${p0?.lastLocation?.latitude}, ${p0?.lastLocation?.longitude}")

            if (p0?.lastLocation?.latitude != null && p0.lastLocation?.longitude != null) {
                viewModel.getData(p0.lastLocation.latitude, p0.lastLocation.longitude)
            }

            fab_refresh.visibility = View.VISIBLE
        }
    }

    override fun initStartView() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        checkPermissions()
    }

    override fun initDataBinding() {
        LocationManager(this, fusedLocationProviderClient, locationCallback)
    }

    @SuppressLint("MissingPermission")
    override fun initAfterBinding() {
        fab_refresh.setOnClickListener {
            viewModel.refresh()
            fusedLocationProviderClient.requestLocationUpdates(LocationRequest().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY), locationCallback, null)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSION_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSION_ACCESS_FINE_LOCATION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}
package com.example.gpslocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), LocationListener {
    private val LOCATION_PERM_CODE = 2
    lateinit var locationManager: LocationManager
    lateinit var connectTxt: TextView
    lateinit var lonTxt: TextView
    lateinit var latTxt: TextView
    lateinit var listProviders: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        connectTxt = findViewById(R.id.connect)
        lonTxt = findViewById(R.id.lon)
        latTxt = findViewById(R.id.lat)
        listProviders = findViewById(R.id.listProvider)

        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERM_CODE
            )
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5f, this)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5f, this)
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 5f, this)

        val prv = locationManager.getBestProvider(Criteria(), true)
        onProviderEnabled("gps")
        if (prv != null) {
            val location = locationManager.getLastKnownLocation(prv)
            if (location != null)
                displayCoord(location.latitude, location.longitude)
        }
    }

    override fun onLocationChanged(loc: Location) {
        val lat = loc.latitude
        val lng = loc.longitude
        displayCoord(lat, lng)
        listProviders.text = locationManager.getProviders(true).toString()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != LOCATION_PERM_CODE) {
            startActivity(Intent(this, MainActivity::class.java))
//            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//
//            }
        }
    }

    override fun onProviderEnabled(provider: String) {
        connectTxt.text = "Online"
        connectTxt.setTextColor(Color.GREEN)
    }

    override fun onProviderDisabled(provider: String) {
        connectTxt.text = "Offline"
        connectTxt.setTextColor(Color.RED)
    }

    fun displayCoord(latitude: Double, longtitude: Double) {
        lonTxt.text = String.format("%.5f", longtitude)
        latTxt.text = String.format("%.5f", latitude)
    }



    // TODO: обработать случай отключения GPS (геолокации) пользователем
    // onProviderDisabled + onProviderEnabled

    // TODO: обработать возврат в активность onRequestPermissionsResult
}
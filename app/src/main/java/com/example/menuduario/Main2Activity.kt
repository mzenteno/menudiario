package com.example.menuduario

import android.Manifest
import com.example.menuduario.databinding.ActivityMain2Binding
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class Main2Activity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrenLocationUser()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setOnMarkerClickListener (this)
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        googleMap.isMyLocationEnabled = true

        val latLng = LatLng( currentLocation.latitude, currentLocation.longitude )

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f), 800, null)

        val markerOptions1 = MarkerOptions().position(LatLng(-17.801072, -63.141653)).title("Restaurante 1")
        val markerOptions2 = MarkerOptions().position(LatLng(-17.801593, -63.135409)).title("Restaurante 2")
        val markerOptions3 = MarkerOptions().position(LatLng(-17.798671,-63.149807)).title("Restadurante 3")

        googleMap.addMarker(markerOptions1)
        googleMap.addMarker(markerOptions2)
        googleMap.addMarker(markerOptions3)
    }

    private fun getCurrenLocationUser() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location ->
            if( location != null ){
                currentLocation = location
                val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Toast.makeText(this, marker.title, Toast.LENGTH_LONG).show()
        return true
    }

}
package com.example.menuduario

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.menuduario.databinding.ActivityMain2Binding
import com.example.menuduario.fragments.FavoriteFragment
import com.example.menuduario.fragments.HomeFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior


class Main2Activity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var binding: ActivityMain2Binding

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    private val homeFragment = HomeFragment()
    private val favoriteFragment = FavoriteFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(homeFragment)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.home ->{
                    replaceFragment(homeFragment)
                    true
                }
                R.id.favorites ->{
                    replaceFragment(favoriteFragment)
                    true
                }
                R.id.comunity ->{
                    replaceFragment(homeFragment)
                    true
                }

                else -> { true }
            }
        }

        //val bottomSheet: View = findViewById(R.id.bottom_sheet)
        //bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

1        //Set location current
        //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        //getCurrenLocationUser()

        //binding.btnMenu.setOnClickListener() {
        //    android.util.Log.i("onMapClick", "Horray!");
        //}
    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setOnMarkerClickListener (this)
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        googleMap.isMyLocationEnabled = true


        googleMap?.setOnMapClickListener {
            android.util.Log.i("onMapClick", "Horray!");
            (bottomSheetBehavior as BottomSheetBehavior<*>).setState(
                BottomSheetBehavior.STATE_COLLAPSED
            )
        }
        googleMap?.setOnCameraMoveListener{
            android.util.Log.i("onMapClick", "Horray!");
            (bottomSheetBehavior as BottomSheetBehavior<*>).setState(
                BottomSheetBehavior.STATE_COLLAPSED
            )
        }

        val latLng = LatLng( currentLocation.latitude, currentLocation.longitude )

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f), 800, null)

        val markerOptions1 = MarkerOptions().position(LatLng(-17.801072, -63.141653)).title("Restaurante 1").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_restaurant))
        val markerOptions2 = MarkerOptions().position(LatLng(-17.801593, -63.135409)).title("Restaurante 2").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_restaurant))
        val markerOptions3 = MarkerOptions().position(LatLng(-17.798671,-63.149807)).title("Restadurante 3").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_restaurant))

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
        (bottomSheetBehavior as BottomSheetBehavior<*>).setState(
            BottomSheetBehavior.STATE_EXPANDED
        )
        //        (bottomSheetBehavior as BottomSheetBehavior<*>).setBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_COLLAPSED -> changingState.text = "Collapsed"
//                    BottomSheetBehavior.STATE_DRAGGING -> changingState.text = "Dragging..."
//                    BottomSheetBehavior.STATE_EXPANDED -> changingState.text = "Expanded"
//                    BottomSheetBehavior.STATE_HIDDEN -> changingState.text = "Hidden"
//                    BottomSheetBehavior.STATE_SETTLING -> changingState.text = "Settling..."
//                }
//            }
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
//        })

        return true
    }

}
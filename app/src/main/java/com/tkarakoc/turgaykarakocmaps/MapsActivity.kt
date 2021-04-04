package com.tkarakoc.turgaykarakocmaps

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        /*val ankara = LatLng(39.9208372, 32.8454561)
        mMap.addMarker(MarkerOptions().position(ankara).title("Marker in Ankara"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ankara, 15f)) */
        
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                mMap.clear()
                val guncelKonum = LatLng(p0.latitude, p0.longitude)
                mMap.addMarker(MarkerOptions().position(guncelKonum).title("Güncel Konumunuz"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(guncelKonum, 15f))

                val geocoder = Geocoder(this@MapsActivity, Locale.getDefault())

                try {

                    val addressList = geocoder.getFromLocation(p0.latitude, p0.longitude, 1)
                    if(addressList.size > 0) {
                        println(addressList.get(0).toString())
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }

        }

    }
}
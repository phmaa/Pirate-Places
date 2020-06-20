package edu.ecu.cs.pirateplaces

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EcuMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var place: PiratePlace

    private val piratePlacesListViewModel : PiratePlacesListViewModel by lazy {
        ViewModelProviders.of(this).get(PiratePlacesListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecu_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        place = PiratePlace()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

/*
    override fun onStart() {
        super.onStart()
        piratePlacesDetailViewModel.piratePlaceLiveData.observe(
            this,
            Observer { piaratePlace ->
                piaratePlace?.let {
                    this.place = piaratePlace
                    var latitude = place.latitude
                    var longitude = place.longitude
                   // place.hasLocation = 1
                }
            }
        )
        piratePlacesDetailViewModel.savePiratePlace(place)
    }

 */

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        piratePlacesListViewModel.piratePlacesListLiveData.observe(
            this,
            Observer {places ->
                places?.let {
                    //val id = place.id
                    val latitude = place.latitude
                    val longitude = place.longitude
                    val latLng = LatLng(latitude, longitude)
                    Log.i("MapActivity", "Got latitude: ${place.latitude}, longitude: ${place.longitude}")
                    if (place.hasLocation == 1) {
                        map.addMarker(MarkerOptions().position(latLng))
                    }
                }

            }
        )
/*
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    place.latitude =  location.latitude
                    place.longitude = location.longitude
                    place.hasLocation = 1
                    Log.i("MapActivity", "Got latitude: ${place.latitude}, longitude: ${place.longitude}")
                }
            }

        piratePlacesDetailViewModel.piratePlaceLiveData.observe(
            this,
            Observer {place ->
                place?.let {
                    this.place = place
                    if (place.hasLocation == 1) {
                        val latLng = LatLng(place.latitude, place.longitude)
                        map.addMarker(MarkerOptions().position(latLng))
                        Log.i("MapActivity2", "Got latitude: ${place.latitude}, longitude: ${place.longitude}")
                    }


                }
            }
        )
        //piratePlacesDetailViewModel.savePiratePlace(place)

 */
        enableMyLocation()
        setMapLongClik(map)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun setMapLongClik(map: GoogleMap) {
       map.setOnMapLongClickListener { latLng ->
           map.addMarker(
               MarkerOptions()
                   .position(latLng)
           )
       }
    }
}

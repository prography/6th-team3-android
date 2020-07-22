package com.prography.pethotel.ui.places.util

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat



class GPSTracker(
    val context: Context,
    var locationManager: LocationManager
) : Service(), LocationListener{

    var location: Location?=null
    var latitude: Double ?= 0.0
    var longitude: Double ?= 0.0

    companion object{
        const val MIN_DISTANCE_CHANGE_FOR_UPDATES = 10.toFloat()
        const val MIN_TIME_BTW_UPDATES = (1000 * 60 * 1).toLong()
        private const val TAG = "DistanceUtil"
    }

    fun getUserLocation() : Location?{
        try{
            val isGPSEnabled = locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )
            val isNetworkEnabled = locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )

            if(!isGPSEnabled && !isNetworkEnabled){

            }else{
                if(!isLocationPermissionsGranted(context)){
                    return null
                }
            }

            if(isNetworkEnabled){
                if (!isLocationPermissionsGranted(context)) {
                    return null
                }
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BTW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this
                )

                location = locationManager.getLastKnownLocation(
                    LocationManager.NETWORK_PROVIDER
                )
                if(location != null){
                    latitude = location!!.latitude
                    longitude = location!!.longitude
                }
            }

            if(isGPSEnabled){
                if(location == null){

                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BTW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this
                    )

                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(
                            LocationManager.GPS_PROVIDER
                        )
                        if(location != null){
                            latitude = location!!.latitude
                            longitude = location!!.longitude
                        }
                    }
                }
            }

        }catch (e : SecurityException){
            Log.d(TAG, "getUserLocation: ${e.printStackTrace()}")
        }
        return location
    }


    fun getUserLatitude() : Double?{
        if(location != null){
            latitude = location!!.latitude
        }
        return latitude
    }

    fun getUserLongitude() : Double?{
        if(location != null){
            longitude = location!!.longitude
        }
        return longitude
    }

    fun stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(this)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location?) {
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

}

private fun isLocationPermissionsGranted(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}
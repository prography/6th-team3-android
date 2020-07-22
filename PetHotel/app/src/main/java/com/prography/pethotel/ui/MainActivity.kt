package com.prography.pethotel.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.prography.pethotel.R
import com.prography.pethotel.ui.places.PlaceInfoViewModel
import com.prography.pethotel.utils.*


private const val TAG = "MainActivity"

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(){

//    private lateinit var mainLayout : LinearLayout
    private lateinit var placeInfoViewModel: PlaceInfoViewModel
    private lateinit var loginStateViewModel: LoginStateViewModel

    private var currentNavController: LiveData<NavController>? = null


    /*location related */
    private lateinit var locationManager : LocationManager

    private lateinit var providers : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        loginStateViewModel = ViewModelProvider(this, LoginStateViewModelFactory())
                                .get(LoginStateViewModel::class.java)

        if(loginStateViewModel.isTokenValid(this)){
            loginStateViewModel.updateTokenState(TokenState.STORED)
            Toast.makeText(this, "환영합니다!", Toast.LENGTH_SHORT).show()
        }else{
            loginStateViewModel.updateTokenState(TokenState.REMOVED)
            val toast = Toast.makeText(this, "토큰없음!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }


        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState


        getLocation()

        placeInfoViewModel = ViewModelProviders.of(this).get(PlaceInfoViewModel::class.java)
        placeInfoViewModel.getHotelLists()
        Log.d(TAG, "onCreate: From Main Activity")

        val pref = getSharedPreferences(USER_LOCATION, Context.MODE_PRIVATE)
        val lat = pref.getString(USER_LAT, "0.0")
        val long = pref.getString(USER_LONG, "0.0")

        placeInfoViewModel.hotelList.observe(this, Observer {
            placeInfoViewModel.setDistanceToHotelList(lat!!.toDouble(), long!!.toDouble())
        })
    }

    override fun onStart() {
        super.onStart()
        requestLocationPermissionIfNotGranted()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_main)

        val navGraphIds = listOf(
            R.navigation.places,
            R.navigation.monitor,
            R.navigation.my_page
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )


        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    fun Fragment.getViewModelStoreOwner(): ViewModelStoreOwner = try {
        requireActivity()
    } catch (e: IllegalStateException) {
        this
    }


    private fun getLocation(){
        if (
            !isLocationPermissionGranted()
        ) {
            requestLocationPermissionIfNotGranted()
        }else{
            Log.d(TAG, "onCreate: Permissions Granted!")
        }
    }

    private fun isLocationPermissionGranted() : Boolean{
        return (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermission(){
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), 100
        )
    }

    private fun requestLocationPermissionIfNotGranted(){
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
                ) &&
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                )){
                requestLocationPermission()
                return
            }else{
                requestLocationPermission()
                return
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 100 ){
// If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                    /*권한이 주어졌으면 위치 정보를 가져온다*/
                    getLocation()

                    val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                    Log.d(TAG, "onRequestPermissionsResult: $location")
                    //위도, 경도 -> double 타입
                    Log.d(TAG, "onRequestPermissionsResult: 위도 ${location.latitude}\n경도 ${location.longitude}")
                    if(location != null) {
                        val pref = getSharedPreferences(USER_LOCATION, Context.MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString(USER_LONG, location.longitude.toString())
                        editor.putString(USER_LAT, location.latitude.toString())
                        editor.apply()
                    }
                }
            }else{
                Toast.makeText(this, "위치권한없음", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

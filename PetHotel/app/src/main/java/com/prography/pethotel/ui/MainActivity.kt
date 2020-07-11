package com.prography.pethotel.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.prography.pethotel.R
import com.prography.pethotel.ui.mypage.MyPageFragment
import com.prography.pethotel.ui.places.PlaceInfoViewModel
import com.prography.pethotel.utils.*
import kotlinx.android.synthetic.main.activity_main.*


private const val TAG = "MainActivity"

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var mainLayout : LinearLayout
    private lateinit var placeInfoViewModel: PlaceInfoViewModel
    private lateinit var loginStateViewModel: LoginStateViewModel

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        mainLayout = main_linear_container

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

        setSupportActionBar(main_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowTitleEnabled(false)
        }

        placeInfoViewModel = ViewModelProviders.of(this).get(PlaceInfoViewModel::class.java)
        placeInfoViewModel.getHotelLists()
        Log.d(TAG, "onCreate: From Main Activity")

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
            R.navigation.reservation,
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

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
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

}

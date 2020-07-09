package com.prography.pethotel.ui

import android.content.Context
import android.content.DialogInterface
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.prography.pethotel.R
import com.prography.pethotel.ui.places.PlaceInfoViewModel
import com.prography.pethotel.utils.DummyData
import com.prography.pethotel.utils.USER_TOKEN
import com.prography.pethotel.utils.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


private const val TAG = "MainActivity"

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var mainLayout : LinearLayout
    private lateinit var placeInfoViewModel: PlaceInfoViewModel

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = getSharedPreferences(USER_TOKEN, Context.MODE_PRIVATE)
        val userToken = pref.getString(USER_TOKEN, "")
        Log.d(TAG, "onCreate: $userToken")

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

    //TODO : 로그인 상태를 뷰모델로 유지해서 Main 의 프래그먼트들이 이에 해당하는 View 를 띄워주도록 한다.


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

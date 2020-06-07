package com.prography.pethotel.ui

import android.content.DialogInterface
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.prography.pethotel.R
import com.prography.pethotel.utils.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var mainLayout : LinearLayout

    private var currentNavController: LiveData<NavController>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayout = main_linear_container

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

        setSupportActionBar(main_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowTitleEnabled(false)
        }

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
            R.navigation.reservation,
            R.navigation.places,
            R.navigation.my_page,
            R.navigation.monitor
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


    override fun onBackPressed() {
        val alertBuilder = MaterialAlertDialogBuilder(this)
        alertBuilder.apply {
            setTitle("종료하기")
            setMessage("마이펫밀리를 종료하시겠습니까?")
            setCancelable(true)
            setPositiveButton("종료") { _, _ ->
                finishAndRemoveTask()
            }
            setNeutralButton("취소") { dialog, _ ->
                dialog.cancel()
            }
        }
        val alertDialog = alertBuilder.create()
        alertDialog.show()
    }

}

package com.prography.pethotel.ui.mypage

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.prography.pethotel.R
import com.prography.pethotel.ui.register.viewmodels.LoginRegisterViewModel
import kotlinx.android.synthetic.main.login_register_fragment.*

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey)
    }


}
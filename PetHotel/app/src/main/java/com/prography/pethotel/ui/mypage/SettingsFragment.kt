package com.prography.pethotel.ui.mypage

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.prography.pethotel.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey)
    }


}
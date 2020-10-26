package com.trilema.doctrinalang.ui.settings

import androidx.preference.PreferenceFragmentCompat
import android.os.Bundle
import com.trilema.doctrinalang.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}
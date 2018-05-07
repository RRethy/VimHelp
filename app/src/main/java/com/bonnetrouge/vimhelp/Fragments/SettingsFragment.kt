package com.bonnetrouge.vimhelp.Fragments

import android.os.Bundle
import android.preference.PreferenceFragment
import com.bonnetrouge.vimhelp.R

class SettingsFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.preferences)
    }
}

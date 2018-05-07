package com.bonnetrouge.vimhelp.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bonnetrouge.vimhelp.R

class SettingsActivity : AppCompatActivity() {

    companion object {
        fun navigate(activity: AppCompatActivity) {
            val i = Intent(activity, SettingsActivity::class.java)
            activity.startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}

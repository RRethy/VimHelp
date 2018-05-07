package com.bonnetrouge.vimhelp.Commons

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity

inline fun AppCompatActivity.editSharedPreferences(editInfo: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    getPreferences(Context.MODE_PRIVATE).edit().editInfo().apply()
}

package com.bonnetrouge.vimhelp.Commons

import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import com.bonnetrouge.vimhelp.VimHelpApp

val app: VimHelpApp
    get() = VimHelpApp.app

const val DTAG = "quman"

fun dog(text: String) {
    Log.d(DTAG, text)
}

inline fun AppCompatActivity.fragmentTransaction(addToBackStack: Boolean = true,
                                                 tag: String? = null,
                                                 swapInfo: FragmentTransaction.() -> FragmentTransaction) {
    if (addToBackStack) supportFragmentManager.beginTransaction().swapInfo().addToBackStack(tag).commit()
    else supportFragmentManager.beginTransaction().swapInfo().commit()
}

fun convertToPixels(sizeInDp: Double): Double {
    val resources = app.getResources()
    val metrics = resources.getDisplayMetrics()
    return sizeInDp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun convertToDp(sizeInPixels: Int): Float {
    val resources = app.getResources()
    val metrics = resources.getDisplayMetrics()
    return sizeInPixels / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

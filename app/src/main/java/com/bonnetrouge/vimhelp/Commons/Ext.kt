package com.bonnetrouge.vimhelp.Commons

import android.util.Log
import com.bonnetrouge.vimhelp.VimHelpApp

val app: VimHelpApp
    get() = VimHelpApp.app

const val DTAG = "quman"

fun dog(text: String) {
    Log.d(DTAG, text)
}

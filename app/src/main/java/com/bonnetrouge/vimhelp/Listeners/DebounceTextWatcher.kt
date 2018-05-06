package com.bonnetrouge.vimhelp.Listeners

import android.text.Editable
import android.text.TextWatcher
import java.util.*

class DebounceTextWatcher(val onDebouncedListener: OnDebouncedListener, val delay: Long) : TextWatcher {
    private var timer = Timer()

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        onDebouncedListener.onPreDebounce(s)
        timer.cancel()
        timer = Timer()
        timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        onDebouncedListener.onDebounced(s)
                    }
                },
                delay
        )
    }

    interface OnDebouncedListener {
        fun onDebounced(s: CharSequence)
        fun onPreDebounce(s: CharSequence)
    }
}

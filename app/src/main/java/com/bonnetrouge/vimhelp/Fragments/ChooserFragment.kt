package com.bonnetrouge.vimhelp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.vimhelp.R
import kotlinx.android.synthetic.main.fragment_chooser.*
import javax.inject.Inject

class ChooserFragment @Inject constructor() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_chooser, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNav.setOnNavigationItemSelectedListener {
            // TODO
            true
        }
    }
}

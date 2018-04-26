package com.bonnetrouge.vimhelp.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bonnetrouge.vimhelp.Commons.app
import com.bonnetrouge.vimhelp.Commons.fragmentTransaction
import com.bonnetrouge.vimhelp.DI.Modules.MainActivityModule
import com.bonnetrouge.vimhelp.Fragments.ChooserFragment
import com.bonnetrouge.vimhelp.R
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var chooserFragment: ChooserFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app.component.plus(MainActivityModule()).inject(this)
        fragmentTransaction { replace(R.id.fragmentContainer, chooserFragment) }
    }
}

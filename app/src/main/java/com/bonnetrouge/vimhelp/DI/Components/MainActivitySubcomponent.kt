package com.bonnetrouge.vimhelp.DI.Components

import com.bonnetrouge.vimhelp.Activities.MainActivity
import com.bonnetrouge.vimhelp.DI.Modules.MainActivityModule
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainActivityModule::class))
interface MainActivitySubcomponent {
    fun inject(activity: MainActivity)
}
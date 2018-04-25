package com.bonnetrouge.vimhelp.DI.Components

import com.bonnetrouge.vimhelp.Activities.MainActivity
import com.bonnetrouge.vimhelp.DI.Modules.AppModule
import com.bonnetrouge.vimhelp.VimHelpApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: VimHelpApp)
    fun plus(activity: MainActivity): MainActivitySubcomponent
}
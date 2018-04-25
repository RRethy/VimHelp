package com.bonnetrouge.vimhelp

import android.app.Application
import com.bonnetrouge.vimhelp.Commons.lazyAndroid
import com.bonnetrouge.vimhelp.DI.Components.AppComponent
import com.bonnetrouge.vimhelp.DI.Components.DaggerAppComponent
import com.bonnetrouge.vimhelp.DI.Modules.AppModule

class VimHelpApp : Application() {

    companion object {
        lateinit var app: VimHelpApp
            private set
    }

    val component: AppComponent by lazyAndroid {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        component.inject(this)
    }
}
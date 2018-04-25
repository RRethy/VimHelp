package com.bonnetrouge.vimhelp.DI.Modules

import com.bonnetrouge.vimhelp.VimHelpApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: VimHelpApp) {

    @Singleton
    @Provides
    fun provideVimHelpApp() = app
}

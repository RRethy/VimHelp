package com.bonnetrouge.vimhelp.DI.Modules

import com.bonnetrouge.vimhelp.Tags.NeovimTagsManager
import com.bonnetrouge.vimhelp.Tags.VimTagsManager
import com.bonnetrouge.vimhelp.VimHelpApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: VimHelpApp) {

    @Singleton
    @Provides
    fun provideVimHelpApp() = app

    @Singleton
    @Provides
    fun provideNeovimTagsManager() = NeovimTagsManager()

    @Singleton
    @Provides
    fun provideVimTagsManager() = VimTagsManager()
}

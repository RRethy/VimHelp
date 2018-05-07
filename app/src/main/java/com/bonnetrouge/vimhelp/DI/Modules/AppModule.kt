package com.bonnetrouge.vimhelp.DI.Modules

import com.bonnetrouge.vimhelp.Commons.QuotesGenerator
import com.bonnetrouge.vimhelp.Tags.TagsManager
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
    fun providesTagsManager() = TagsManager()

    @Singleton
    @Provides
    fun providesRandomQuoteGenerator() = QuotesGenerator()


}

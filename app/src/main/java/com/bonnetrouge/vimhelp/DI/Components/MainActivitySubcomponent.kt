package com.bonnetrouge.vimhelp.DI.Components

import com.bonnetrouge.vimhelp.Activities.MainActivity
import com.bonnetrouge.vimhelp.DI.Modules.MainActivityModule
import com.bonnetrouge.vimhelp.Fragments.BookmarksFragment
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainActivityModule::class))
interface MainActivitySubcomponent {
    fun inject(activity: MainActivity)
    fun inject(bookmarksFragment: BookmarksFragment)
}
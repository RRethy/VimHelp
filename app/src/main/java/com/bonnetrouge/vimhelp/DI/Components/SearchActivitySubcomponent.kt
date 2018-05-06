package com.bonnetrouge.vimhelp.DI.Components

import com.bonnetrouge.vimhelp.Activities.SearchActivity
import com.bonnetrouge.vimhelp.DI.Modules.AppModule
import dagger.Subcomponent

@Subcomponent(modules = [])
interface SearchActivitySubcomponent {
    fun inject(activity: SearchActivity)
}

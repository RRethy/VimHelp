package com.bonnetrouge.vimhelp.DI.Modules

import android.support.v7.app.AppCompatActivity
import com.bonnetrouge.vimhelp.Fragments.*
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule constructor(val activity: AppCompatActivity) {

    @Provides fun provideActivity() = activity

    @Provides fun provideNeovimFragment(activity: AppCompatActivity): NeovimFragment {
        return if (activity.supportFragmentManager.findFragmentByTag(NeovimFragment.TAG) == null) {
            return NeovimFragment()
        } else activity.supportFragmentManager.findFragmentByTag(NeovimFragment.TAG) as NeovimFragment
    }


    @Provides fun provideVimFragment(activity: AppCompatActivity): VimFragment {
        return if (activity.supportFragmentManager.findFragmentByTag(VimFragment.TAG) == null) {
            return VimFragment()
        } else activity.supportFragmentManager.findFragmentByTag(VimFragment.TAG) as VimFragment
    }
}

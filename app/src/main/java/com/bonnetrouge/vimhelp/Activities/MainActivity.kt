package com.bonnetrouge.vimhelp.Activities

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.bonnetrouge.vimhelp.Commons.QuotesGenerator
import com.bonnetrouge.vimhelp.Commons.app
import com.bonnetrouge.vimhelp.Commons.fragmentTransaction
import com.bonnetrouge.vimhelp.Commons.lazyAndroid
import com.bonnetrouge.vimhelp.DI.Modules.MainActivityModule
import com.bonnetrouge.vimhelp.Fragments.NeovimFragment
import com.bonnetrouge.vimhelp.Fragments.VimFragment
import com.bonnetrouge.vimhelp.Interfaces.OnNavigationListener
import com.bonnetrouge.vimhelp.R
import com.bonnetrouge.vimhelp.Tags.TagsManager
import com.bonnetrouge.vimhelp.ViewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        fun navigate(activity: AppCompatActivity) {
            val i = Intent(activity, MainActivity::class.java)
            activity.startActivity(i)
        }
    }

    @Inject lateinit var vimFragment: VimFragment
    @Inject lateinit var neovimFragment: NeovimFragment

    @Inject lateinit var quotesGenerator: QuotesGenerator
    @Inject lateinit var tagsManager: TagsManager

    private val fragmentTags = arrayOf(VimFragment.TAG, NeovimFragment.TAG)
    private val fragments by lazyAndroid { arrayOf<Fragment>(vimFragment, neovimFragment) }

    val viewModel by lazyAndroid { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    val mainActivityComponent by lazyAndroid { app.component.plus(MainActivityModule(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityComponent.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupContent(savedInstanceState)
        setupBottomNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_settings -> {
                SettingsActivity.navigate(this)
            }
            R.id.menu_search -> {
                val fragmentTag = if (viewModel.fragmentIndex == 0) VimFragment.TAG else NeovimFragment.TAG
                SearchActivity.navigate(this, fragmentTag)
            }
            R.id.menu_go_forward -> (fragments[viewModel.fragmentIndex] as OnNavigationListener).onForwardPressed()
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        if ((fragments[viewModel.fragmentIndex] as OnNavigationListener).onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SearchActivity.SEARCH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            when (viewModel.fragmentIndex) {
                0 -> vimFragment.updateUrl(data?.data?.toString())
                1 -> neovimFragment.updateUrl(data?.data?.toString())
            }
        }
    }

    private fun setupContent(savedInstanceState: Bundle?) {
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            if (preferences.getString(getString(R.string.default_docset_key), resources.getStringArray(R.array.default_docset_values)[0])
                    == resources.getStringArray(R.array.default_docset_values)[0]) {
                fragmentTransaction(false) { add(R.id.fragmentContainer, neovimFragment, fragmentTags[1]) }
                bottomNav.selectedItemId = R.id.item_neovim
            } else {
                fragmentTransaction(false) { add(R.id.fragmentContainer, vimFragment, fragmentTags[0]) }
                bottomNav.selectedItemId = R.id.item_vim
            }
        }
    }

    private fun setupBottomNavigation() {
        bottomNav.setOnNavigationItemSelectedListener {
            val nextFragmentIndex = when (it.itemId) {
                R.id.item_vim -> 0
                R.id.item_neovim -> 1
                else -> 1
            }
            fragmentTransaction(false) {
                if (supportFragmentManager.findFragmentByTag(fragmentTags[nextFragmentIndex]) == null) {
                    add(R.id.fragmentContainer, fragments[nextFragmentIndex], fragmentTags[nextFragmentIndex])
                }
                hide(fragments[viewModel.fragmentIndex])
                viewModel.fragmentIndex = nextFragmentIndex
                show(fragments[viewModel.fragmentIndex])
            }
            true
        }
    }
}

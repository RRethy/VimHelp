package com.bonnetrouge.vimhelp.Activities

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bonnetrouge.vimhelp.Commons.app
import com.bonnetrouge.vimhelp.Commons.dog
import com.bonnetrouge.vimhelp.Commons.fragmentTransaction
import com.bonnetrouge.vimhelp.Commons.lazyAndroid
import com.bonnetrouge.vimhelp.DI.Modules.MainActivityModule
import com.bonnetrouge.vimhelp.Fragments.NeovimFragment
import com.bonnetrouge.vimhelp.Fragments.VimFragment
import com.bonnetrouge.vimhelp.Interfaces.OnNavigationListener
import com.bonnetrouge.vimhelp.R
import com.bonnetrouge.vimhelp.ViewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var vimFragment: VimFragment
    @Inject lateinit var neovimFragment: NeovimFragment

    private val fragmentTags = arrayOf(VimFragment.TAG, NeovimFragment.TAG)
    private val fragments by lazyAndroid { arrayOf<Fragment>(vimFragment, neovimFragment) }

    val viewModel by lazyAndroid { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    var menu: Menu? = null

    val mainActivityComponent by lazyAndroid { app.component.plus(MainActivityModule(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityComponent.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            fragmentTransaction(false) { add(R.id.fragmentContainer, neovimFragment, fragmentTags[1]) }
            bottomNav.selectedItemId = R.id.item_neovim
        }

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

        test()
    }

    fun test() {
        BufferedReader(InputStreamReader(assets.open("neovim/tags.txt")))
                .use {
                    var line: String? = it.readLine()

                    while (line != null) {
                        dog(line)
                        line = it.readLine()
                        dog(line)
                        line = it.readLine()
                    }
                }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_main, menu)

        this.menu = menu

        menu?.findItem(R.id.menu_search)?.setOnMenuItemClickListener {
            SearchActivity.navigate(this)
            true
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_settings -> Toast.makeText(this, "Clicked settings", Toast.LENGTH_SHORT).show()
            R.id.menu_search -> Toast.makeText(this, "Clicked search", Toast.LENGTH_SHORT).show()
            R.id.menu_go_forward -> {
                if (!(fragments[viewModel.fragmentIndex] as OnNavigationListener).onForwardPressed()) {
                    // TODO: Think of something to do when there's no where forward to navigate
                }
            }
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
}

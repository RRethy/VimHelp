package com.bonnetrouge.vimhelp.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bonnetrouge.vimhelp.Commons.app
import com.bonnetrouge.vimhelp.Commons.fragmentTransaction
import com.bonnetrouge.vimhelp.Commons.lazyAndroid
import com.bonnetrouge.vimhelp.DI.Modules.MainActivityModule
import com.bonnetrouge.vimhelp.Fragments.BookmarksFragment
import com.bonnetrouge.vimhelp.Fragments.NeovimFragment
import com.bonnetrouge.vimhelp.Fragments.VimFragment
import com.bonnetrouge.vimhelp.Interfaces.OnBackPressedListener
import com.bonnetrouge.vimhelp.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var vimFragment: VimFragment
    @Inject lateinit var neovimFragment: NeovimFragment
    @Inject lateinit var bookmarksFragment: BookmarksFragment
    private val fragmentTags = arrayOf("vim", "neovim", "bookmarks")
    private val fragments by lazyAndroid { arrayOf(vimFragment, neovimFragment, bookmarksFragment) }
    private var previousFragment = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app.component.plus(MainActivityModule()).inject(this)
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            fragmentTransaction(false) { add(R.id.fragmentContainer, neovimFragment, fragmentTags[1]) }
            bottomNav.selectedItemId = R.id.item_neovim
        }
        bottomNav.setOnNavigationItemSelectedListener {
            val nextFragmentPos = when (it.itemId) {
                R.id.item_vim -> 0
                R.id.item_neovim -> 1
                R.id.item_bookmarks -> 2
                else -> 1
            }
            fragmentTransaction(false) {
                if (supportFragmentManager.findFragmentByTag(fragmentTags[nextFragmentPos]) == null) {
                    add(R.id.fragmentContainer, fragments[nextFragmentPos], fragmentTags[nextFragmentPos])
                }
                hide(fragments[previousFragment])
                previousFragment = nextFragmentPos
                show(fragments[nextFragmentPos])
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_settings -> {
                Toast.makeText(this, "Clicked settings", Toast.LENGTH_SHORT)
                return true
            }
            R.id.menu_search -> {
                Toast.makeText(this, "Clicked search", Toast.LENGTH_SHORT)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (fragments[previousFragment] is OnBackPressedListener) {
            if ((fragments[previousFragment] as OnBackPressedListener).onBackPressed())
                return
        }
        super.onBackPressed()
    }
}

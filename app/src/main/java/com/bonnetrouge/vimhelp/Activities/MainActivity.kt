package com.bonnetrouge.vimhelp.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bonnetrouge.vimhelp.Commons.app
import com.bonnetrouge.vimhelp.Commons.fragmentTransaction
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app.component.plus(MainActivityModule()).inject(this)
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            fragmentTransaction(false) { add(R.id.fragmentContainer, neovimFragment) }
            bottomNav.selectedItemId = R.id.item_neovim
        }
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_vim -> fragmentTransaction(false) { replace(R.id.fragmentContainer, vimFragment) }
                R.id.item_neovim -> fragmentTransaction(false) { replace(R.id.fragmentContainer, neovimFragment) }
                R.id.item_bookmarks -> fragmentTransaction(false) { replace(R.id.fragmentContainer, bookmarksFragment) }
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
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) is OnBackPressedListener) {
            if ((supportFragmentManager.findFragmentById(R.id.fragmentContainer) as OnBackPressedListener).onBackPressed())
                return
        }
        super.onBackPressed()
    }
}

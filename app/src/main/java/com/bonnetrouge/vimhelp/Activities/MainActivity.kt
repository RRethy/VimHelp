package com.bonnetrouge.vimhelp.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bonnetrouge.vimhelp.Commons.app
import com.bonnetrouge.vimhelp.Commons.dog
import com.bonnetrouge.vimhelp.Commons.fragmentTransaction
import com.bonnetrouge.vimhelp.DI.Modules.MainActivityModule
import com.bonnetrouge.vimhelp.Fragments.BookmarksFragment
import com.bonnetrouge.vimhelp.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var vimFragment: Fragment
    @Inject lateinit var neovimFragment: Fragment
    @Inject lateinit var bookmarksFragment: BookmarksFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app.component.plus(MainActivityModule()).inject(this)
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            fragmentTransaction { add(R.id.fragmentContainer, neovimFragment) }
            bottomNav.selectedItemId = R.id.item_neovim
        }
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_vim -> fragmentTransaction { replace(R.id.fragmentContainer, vimFragment) }
                R.id.item_neovim -> fragmentTransaction { replace(R.id.fragmentContainer, neovimFragment) }
                R.id.item_bookmarks -> fragmentTransaction { replace(R.id.fragmentContainer, bookmarksFragment) }
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
        }
        return super.onOptionsItemSelected(item)
    }
}

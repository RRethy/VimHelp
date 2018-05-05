package com.bonnetrouge.vimhelp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.vimhelp.Activities.MainActivity
import com.bonnetrouge.vimhelp.Commons.lazyAndroid
import com.bonnetrouge.vimhelp.Interfaces.OnNavigationListener
import com.bonnetrouge.vimhelp.R
import kotlinx.android.synthetic.main.fragment_bookmarks.*
import javax.inject.Inject

class BookmarksFragment : Fragment(), OnNavigationListener {

    companion object {
        const val TAG = "BOOKMARKS"
    }

    @Inject lateinit var vimBookmarksFragment: VimBookmarksFragment
    @Inject lateinit var neovimBookmarksFragment: NeovimBookmarksFragment

    private val adapter by lazyAndroid { BookmarksPagerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).mainActivityComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_bookmarks, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onBackPressed() = false

    override fun onForwardPressed() = false

    inner class BookmarksPagerAdapter : FragmentPagerAdapter(this.childFragmentManager) {

        val titles = arrayOf(R.string.neovim, R.string.vim)

        override fun getPageTitle(position: Int): CharSequence? {
            return getString(titles[position])
        }

        override fun getItem(position: Int): Fragment {
            return if (position == 0) neovimBookmarksFragment
            else vimBookmarksFragment
        }

        override fun getCount() = 2
    }
}
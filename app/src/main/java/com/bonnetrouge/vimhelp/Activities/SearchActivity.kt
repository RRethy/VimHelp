package com.bonnetrouge.vimhelp.Activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.bonnetrouge.vimhelp.Adapters.CompletionAdapter
import com.bonnetrouge.vimhelp.Commons.*
import com.bonnetrouge.vimhelp.Listeners.DebounceTextWatcher
import com.bonnetrouge.vimhelp.R
import com.bonnetrouge.vimhelp.Tags.NeovimTagsManager
import com.bonnetrouge.vimhelp.Tags.VimTagsManager
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject


class SearchActivity : AppCompatActivity(), DebounceTextWatcher.OnDebouncedListener {

    companion object {
        const val SEARCH_REQUEST_CODE = 1867

        fun navigate(activity: AppCompatActivity) {
            val i = Intent(activity, SearchActivity::class.java)
            activity.startActivityForResult(i, SEARCH_REQUEST_CODE)
        }
    }

    @Inject lateinit var neovimTagsManager: NeovimTagsManager
    @Inject lateinit var vimTagsManager: VimTagsManager

    private val debounceTextWatcher by lazyAndroid { DebounceTextWatcher(this, 300) }

    private val adapter: CompletionAdapter by lazyAndroid { CompletionAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        app.component.plus().inject(this)

        setSupportActionBar(toolbar)

        completionRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        completionRecyclerView.adapter = adapter

        adapter.notifyItemRangeInserted(0, 9)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupSearchEditText()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) onBackPressed()

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        setResult(Activity.RESULT_CANCELED)

        finish()
    }

    override fun onDebounced(s: CharSequence) {
        runOnUiThread {
            val newSuggestions = neovimTagsManager.nvimTags
                    .fuzzyFilter(MED_FUZZY, s) { this }
            if (!newSuggestions.isEmpty()) {
                // TODO: hide no results found
                val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                        return adapter.suggestions[oldItemPosition] == newSuggestions[newItemPosition]
                    }

                    override fun getOldListSize() = adapter.suggestions.size

                    override fun getNewListSize() = newSuggestions.size

                    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true
                })
                adapter.suggestions.clear()
                adapter.suggestions.addAll(newSuggestions)
                diffResult.dispatchUpdatesTo(adapter)
            } else {
                // TODO: show no results found
            }
        }
    }

    override fun onPreDebounce(s: CharSequence) {
        if (s.isEmpty()) {
            clearSearchButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_clear_grey_24dp))
        } else {
            clearSearchButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_clear_white_24dp))
        }
    }
    private fun setupSearchEditText() {
        searchEditText.addTextChangedListener(debounceTextWatcher)
        clearSearchButton.setOnClickListener { searchEditText.setText("") }
    }

    fun onSuggestionClicked(suggestion: String) {
        val data = Intent()
        data.data = Uri.parse(suggestion)
        setResult(Activity.RESULT_OK)

        finish()
    }
}

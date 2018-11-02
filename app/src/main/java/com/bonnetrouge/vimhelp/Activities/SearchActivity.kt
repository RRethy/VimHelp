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
import android.view.View
import com.bonnetrouge.vimhelp.Adapters.SuggestionsAdapter
import com.bonnetrouge.vimhelp.Commons.*
import com.bonnetrouge.vimhelp.Fragments.NeovimFragment
import com.bonnetrouge.vimhelp.Listeners.DebounceTextWatcher
import com.bonnetrouge.vimhelp.R
import com.bonnetrouge.vimhelp.Tags.Tag
import com.bonnetrouge.vimhelp.Tags.TagsManager
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newSingleThreadContext
import javax.inject.Inject


class SearchActivity : AppCompatActivity(), DebounceTextWatcher.OnDebouncedListener {

    companion object {
        const val SEARCH_REQUEST_CODE = 1867
        const val FRAGMENT_TAG = "CURRENT_FRAGMENT"

        fun navigate(activity: AppCompatActivity, fragmentTag: String) {
            val i = Intent(activity, SearchActivity::class.java)
            i.putExtra(FRAGMENT_TAG, fragmentTag)
            activity.startActivityForResult(i, SEARCH_REQUEST_CODE)
        }
    }

    @Inject lateinit var tagsManager: TagsManager

    @Inject lateinit var quotesGenerator: QuotesGenerator

    private val debounceTextWatcher by lazyAndroid { DebounceTextWatcher(this, 300) }

    private val adapter: SuggestionsAdapter by lazyAndroid { SuggestionsAdapter(this) }

    private var filterJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        app.component.plus().inject(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
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
        filterJob?.cancel()
        filterJob = launch {
            val newSuggestions = tagsManager.tags(intent.getStringExtra(FRAGMENT_TAG))
                    .fuzzyFilter(MED_FUZZY, s) { this.tag }
            if (!isActive) return@launch

            runOnUiThread {
                if (!newSuggestions.isEmpty()) {
                    hiddenQuote.visibility = View.GONE
                    adapter.submitList(newSuggestions)
                } else {
                    adapter.submitList(null)
                    hiddenQuote.visibility = View.VISIBLE
                    hiddenQuote.text = quotesGenerator.getRandomQuote()
                }

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

    private fun setupRecyclerView() {
        completionRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        completionRecyclerView.adapter = adapter
        adapter.submitList(tagsManager.tags(intent.getStringExtra(FRAGMENT_TAG)))
    }

    private fun setupSearchEditText() {
        searchEditText.addTextChangedListener(debounceTextWatcher)
        clearSearchButton.setOnClickListener { searchEditText.setText("") }
    }

    fun onSuggestionClicked(suggestion: Tag) {
        val data = Intent()
        data.data = Uri.parse(suggestion.uri)
        setResult(Activity.RESULT_OK, data)

        finish()
    }
}

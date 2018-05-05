package com.bonnetrouge.vimhelp.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.bonnetrouge.vimhelp.Commons.lazyAndroid
import com.bonnetrouge.vimhelp.CompletionAdapter
import com.bonnetrouge.vimhelp.R
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {

    companion object {
        fun navigate(activity: AppCompatActivity) {
            val i = Intent(activity, SearchActivity::class.java)
            activity.startActivity(i)
        }
    }

    val adapter: CompletionAdapter by lazyAndroid { CompletionAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)

        clearSearchButton.setOnClickListener { searchEditText.setText("") }

        completionRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        completionRecyclerView.adapter = adapter

        adapter.notifyItemRangeInserted(0, 9)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) onBackPressed()

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}

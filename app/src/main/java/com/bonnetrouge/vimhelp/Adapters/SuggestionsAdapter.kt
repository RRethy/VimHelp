package com.bonnetrouge.vimhelp.Adapters

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bonnetrouge.vimhelp.Activities.SearchActivity
import com.bonnetrouge.vimhelp.Commons.bindView
import com.bonnetrouge.vimhelp.R
import com.bonnetrouge.vimhelp.Tags.Tag
import java.util.regex.Pattern

class SuggestionsAdapter(val searchActivity: SearchActivity) :
        ListAdapter<Tag, SuggestionsAdapter.SuggestionViewHolder>(Tag.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        return SuggestionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_suggestion, parent, false))
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class SuggestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val suggestionTag: TextView by bindView(R.id.suggestionTag)
        val suggestionFileName: TextView by bindView(R.id.suggestionFileName)

        private val neovimPattern: Pattern = Pattern.compile("file:///android_asset/.*/(.*).html#.*")
        private val vimPattern: Pattern = Pattern.compile("file:///android_asset/.*/(.*).txt.html#.*")

        init {
            view.setOnClickListener { searchActivity.onSuggestionClicked(getItem(adapterPosition)) }
        }

        fun onBind(tag: Tag) {
            suggestionTag.text = tag.tag
            val matcher = if (tag.uri.contains(".txt")) { // Vim docs have the suffix .txt.html
                vimPattern.matcher(tag.uri)
            } else { // Neovim docs only have the suffix .html
                neovimPattern.matcher(tag.uri)
            }
            if (matcher.find()) {
                suggestionFileName.text = String.format("%s.txt", matcher.group(1))
            }
        }
    }
}

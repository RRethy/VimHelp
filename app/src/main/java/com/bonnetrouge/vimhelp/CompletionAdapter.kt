package com.bonnetrouge.vimhelp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bonnetrouge.vimhelp.Commons.bindView

class CompletionAdapter : RecyclerView.Adapter<CompletionAdapter.SuggestionViewHolder>() {

    val suggestions = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        return SuggestionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_suggestion, parent, false))
    }

    override fun getItemCount() = suggestions.size

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.onBind(suggestions[position])
    }

    inner class SuggestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val suggestionText: TextView by bindView(R.id.suggestionText)

        fun onBind(s: String) {
            suggestionText.text = s
        }
    }
}

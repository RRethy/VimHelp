package com.bonnetrouge.vimhelp.Tags

import android.support.v7.util.DiffUtil

data class Tag(val tag: String, val uri: String) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tag>() {
            override fun areItemsTheSame(s1: Tag, s2: Tag) = s1 == s2

            override fun areContentsTheSame(s1: Tag, s2: Tag) = true
        }
    }
}

package com.bonnetrouge.vimhelp.Tags

import com.bonnetrouge.vimhelp.Commons.app
import com.bonnetrouge.vimhelp.Commons.dog
import com.bonnetrouge.vimhelp.Fragments.VimFragment
import kotlinx.coroutines.experimental.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Singleton

@Singleton
class TagsManager {

    private val vimTags = ArrayList<Tag>()
    private val nvimTags = ArrayList<Tag>()

    init {
        initNvimTags()
        initVimTags()
    }

    fun tags(choice: String): ArrayList<Tag> {
        return if (choice == VimFragment.TAG) vimTags
        else nvimTags
    }

    private fun initNvimTags() {
        launch {
            BufferedReader(InputStreamReader(app.assets.open("tags/neovim_tags.txt")))
                    .use {
                        var line: String? = it.readLine()
                        var line2: String? = it.readLine()

                        while (true) {
                            nvimTags.add(Tag(line!!, line2!!))
                            line = it.readLine()
                            if (line == null) break
                            line2 = it.readLine()
                        }
                    }
        }
    }

    private fun initVimTags() {
        launch {
            BufferedReader(InputStreamReader(app.assets.open("tags/vim_tags.txt")))
                    .use {
                        var line: String? = it.readLine()
                        var line2: String? = it.readLine()

                        while (true) {
                            vimTags.add(Tag(line!!, line2!!))
                            line = it.readLine()
                            if (line == null) break
                            line2 = it.readLine()
                        }
                    }
        }

    }
}

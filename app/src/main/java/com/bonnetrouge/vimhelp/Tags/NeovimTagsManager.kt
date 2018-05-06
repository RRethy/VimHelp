package com.bonnetrouge.vimhelp.Tags

import com.bonnetrouge.vimhelp.Commons.app
import com.bonnetrouge.vimhelp.VimHelpApp
import kotlinx.coroutines.experimental.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Singleton

@Singleton
class NeovimTagsManager {

    val nvimUrlsMap = HashMap<String, String>()
    val nvimTags = ArrayList<String>()

    init {
        launch {
            BufferedReader(InputStreamReader(app.assets.open("tags/neovim_tags.txt")))
                    .use {
                        var line: String? = it.readLine()
                        var line2: String? = it.readLine()

                        while (true) {
                            nvimTags.add(line!!)
                            nvimUrlsMap[line] = line2!!
                            line = it.readLine()
                            if (line == null) break
                            line2 = it.readLine()
                        }
                    }
        }
    }
}

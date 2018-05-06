package com.bonnetrouge.vimhelp.Commons

import android.os.SystemClock
import java.util.*
import javax.inject.Singleton

@Singleton
class QuotesGenerator {

    private val quotes: ArrayList<String> = arrayListOf()

    private var randnum: Random = Random()

    init {
        randnum.setSeed(SystemClock.elapsedRealtimeNanos())

        quotes.add("vim (vim) noun - Ebullient vitality and energy.  [Latin, accusative of vis, strength]\n(Dictionary)")
        quotes.add("I was a 12-year emacs user who switched to Vim about a year ago after finally giving up on the multiple incompatible versions, flaky contributed packages, disorganized keystrokes, etc.  And it was one of the best moves I ever made.\n(Joel Burton)")
        quotes.add("Vi Vi Vi is the editor of the beast\n(St. IGNUcius)")
        quotes.add("A previous girlfriend of mine switched to emacs.  Needless to say, the relationship went nowhere.\n(Geoffrey Mann)")
        quotes.add("Having recently succeeded in running Vim via telnet through a Nokia Communicator, I can now report that it works nicely on a Palm Pilot too\n(Allan Kelly, Scotland)")
        quotes.add("I love and use VIM heavily too.\n(Larry Wall)")
        quotes.add("This guide was written using the Windows 9.x distribution of GVIM, which is quite possibly the greatest thing to come along since God created the naked girl\n(Michael DiBernardo)")
        quotes.add("I luv VIM.  It is incredible.  I'm naming my first-born Vimberly.\n(Jose Unpingco, USA)")
        quotes.add("VIM is the greatest editor since the stone chisel\n(Jose Unpingco, USA)")
        quotes.add("I even write my sheet music in Vim\n(Ludwig van Beethoven, Germany)")
        quotes.add("So let it be written in Vim, so let it be done\n(Pharaoh Rameses II)")
        quotes.add("I'm gonna make him an offer he can't refuse. :offer!\n(Vito Corleone)")
        quotes.add("Toto, I've got a feeling we're not in Vi anymore\n(Dorothy Gale, Kansas)")
        quotes.add("I love the smell of Vim in the morning\n(Bill Kilgore)")
        quotes.add("Vim. Neovim.\n(James Bond)")
        quotes.add("You know how to quit, don't you, Steve? You just put :wq and press enter.\n(Marie Browning)")
        quotes.add("I'll be #000000\n(Terminator)")
        quotes.add("We use Vim\n(Clyde Barrow)")
        quotes.add("I see emacs users\n(Cole Sear)")
        quotes.add(":help, we have a problem\n(Jim Lovell)")
        quotes.add("You've got to ask yourself one question: 'Do I feel lucky?' Well, do ya, punk?\n(Netrw)")
        quotes.add("Elementary, my dear non-Vim user\n(Sherlock Holmes)")
        quotes.add("Take your stinking paws off me, you damned dirty emacs user\n(George Tyler)")
        quotes.add("Wait a minute, wait a minute. You ain't seen nothin' yet!\n(Vim users)")
        quotes.add("A editor. Modal, not stuck in insert.\n(James Bond)")
        quotes.add("I feel the need - the need for speed!\n(Maverick)")
    }

    fun getRandomQuote(): String {
        val n = randnum.nextInt(26 + 1)
        return quotes[n - 1]
    }
}

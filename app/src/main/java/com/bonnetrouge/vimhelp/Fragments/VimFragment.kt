package com.bonnetrouge.vimhelp.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bonnetrouge.vimhelp.Activities.MainActivity
import com.bonnetrouge.vimhelp.Commons.lazyAndroid
import com.bonnetrouge.vimhelp.Interfaces.OnNavigationListener
import com.bonnetrouge.vimhelp.R
import com.bonnetrouge.vimhelp.WebViewClients.BrowsingDocsWebClient
import kotlinx.android.synthetic.main.fragment_vim.*
import javax.inject.Inject

class VimFragment : Fragment(), OnNavigationListener {

    companion object {
        const val TAG = "VIM"
    }

    private val browsingDocsWebClient by lazyAndroid { BrowsingDocsWebClient(activity as Context, (activity as MainActivity).quotesGenerator) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_vim, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.useWideViewPort = true
        webView.setInitialScale(200)
        webView.webViewClient = browsingDocsWebClient
        if (savedInstanceState == null) {
            webView.loadUrl("file:///android_asset/vim/help.html")
        } else {
            webView.restoreState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView?.saveState(outState)

        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed(): Boolean {
        return if (webView != null && webView.canGoBackOrForward(-1)) {
            webView.goBackOrForward(-1)
            true
        } else false
    }

    override fun onForwardPressed(): Boolean {
        return if (webView != null && webView.canGoBackOrForward(1)) {
            webView.goBackOrForward(1)
            true
        } else {
            false
        }
    }

    fun updateUrl(url: String?) {
        url?.let { webView.loadUrl(url) }
    }
}
package com.bonnetrouge.vimhelp.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.vimhelp.Interfaces.OnBackPressedListener
import com.bonnetrouge.vimhelp.R
import com.bonnetrouge.vimhelp.WebViewClients.BrowsingDocsWebClient
import kotlinx.android.synthetic.main.fragment_vim.*
import javax.inject.Inject

class VimFragment @Inject constructor() : Fragment(), OnBackPressedListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_vim, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.useWideViewPort = true
        webView.setInitialScale(200)
        webView.webViewClient = BrowsingDocsWebClient(activity as Context)
        webView.loadUrl("file:///android_asset/vim/help.html")
    }

    override fun onBackPressed(): Boolean {
        return if (webView.canGoBack()) {
            webView.goBack()
            true
        } else false
    }
}
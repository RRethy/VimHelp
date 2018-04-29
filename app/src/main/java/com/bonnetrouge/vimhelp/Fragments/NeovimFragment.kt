package com.bonnetrouge.vimhelp.Fragments

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.vimhelp.R
import com.bonnetrouge.vimhelp.WebViewClients.NvimWebViewClient
import kotlinx.android.synthetic.main.fragment_neovim.*
import javax.inject.Inject

class NeovimFragment @Inject constructor() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_neovim, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.useWideViewPort = true
        webView.setInitialScale(200)
        webView.webViewClient = NvimWebViewClient()
        webView.loadUrl("file:///android_asset/neovim/help.html")
    }
}
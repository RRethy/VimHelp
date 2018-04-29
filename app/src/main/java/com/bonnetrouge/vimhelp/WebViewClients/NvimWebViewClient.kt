package com.bonnetrouge.vimhelp.WebViewClients

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class NvimWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
    }
}
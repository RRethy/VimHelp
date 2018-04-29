package com.bonnetrouge.vimhelp.WebViewClients

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class NvimWebViewClient(val ctx: Context) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (request?.url.toString().startsWith("file:///android_asset/")) {
            view?.loadUrl(request?.url.toString())
        } else {
            val url = request?.url.toString()
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            ctx.startActivity(i)
        }
        return true
    }
}
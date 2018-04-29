package com.bonnetrouge.vimhelp.WebViewClients

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.bonnetrouge.vimhelp.Commons.dog

class BrowsingDocsWebClient(val ctx: Context) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        dog(request?.url.toString())
        return if (request?.url.toString().startsWith("file:///android_asset/")) {
            super.shouldOverrideUrlLoading(view, request)
        } else if (request?.url.toString().endsWith("email-protection")) {
            Toast.makeText(ctx, "This email is protected", Toast.LENGTH_SHORT).show()
            true
        } else {
            val url = request?.url.toString()
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            ctx.startActivity(i)
            true
        }
    }
}
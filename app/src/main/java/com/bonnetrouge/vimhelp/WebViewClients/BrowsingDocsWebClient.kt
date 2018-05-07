package com.bonnetrouge.vimhelp.WebViewClients

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.bonnetrouge.vimhelp.Commons.QuotesGenerator

class BrowsingDocsWebClient constructor(val ctx: Context, val quotesGenerator: QuotesGenerator) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return if (request?.url.toString().startsWith("file:///android_asset/")) {
            super.shouldOverrideUrlLoading(view, request)
        } else if (request?.url.toString().contains("email-protection")
                || request?.url.toString().contains("ftp://ftp.vim.org")) {
            Toast.makeText(ctx, quotesGenerator.getRandomQuote(), Toast.LENGTH_LONG).show()
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
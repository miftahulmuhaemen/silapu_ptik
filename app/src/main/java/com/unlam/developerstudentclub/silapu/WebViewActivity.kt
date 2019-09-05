package com.unlam.developerstudentclub.silapu

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v) {
            backBtn -> finish()
        }
    }

    companion object{
        const val WebViewIdentifier = "WebView"
        const val LAPOR = "https://www.lapor.go.id"
        const val PENGUMUMAN = "https://ulm.ac.id/id/category/pengumuman/"
        const val KEGIATAN = "https://ulm.ac.id/id/kegiatan"
        const val INFORMASI = "https://ppid.ulm.ac.id/id/daftar-informasi-publik-dip/"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = intent?.getStringExtra(WebViewIdentifier)
        webview.webViewClient = WebClient()
        webview.settings.javaScriptEnabled = true
        webview.settings.loadsImagesAutomatically = true
        webview.settings.domStorageEnabled = true
        webview.settings.allowFileAccess = true
        webview.settings.setAppCacheEnabled(true)
        webview.scrollBarSize = View.SCROLLBARS_INSIDE_OVERLAY
        webview.loadUrl(url)
        backBtn.setOnClickListener(this)
    }

    inner class WebClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progress_circular.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView, url: String?) {
            super.onPageFinished(view, url)
            progress_circular.visibility = View.GONE
        }

    }
}

package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.myapplication.utils.GuruJavaScriptInterface

class BookSearchWebView : AppCompatActivity() {

    private val URL = "https://outsourcing-guru-book.vercel.app/book-search"
    private lateinit var webView: WebView

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_search_web_view)

        webView = findViewById(R.id.bookSearchWebView)

        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.builtInZoomControls = false
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
//        settings.cacheMode = WebSettings.
        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(false)
        settings.setSupportZoom(false)

        // 여기서 설장한 이름을 통하여 JavaScript 에서 호출할 수 있게 된다.
        webView.addJavascriptInterface(GuruJavaScriptInterface(this), "AndroidGuru2")

        webView.loadUrl(URL)
    }
}
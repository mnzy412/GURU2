package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import com.example.myapplication.utils.GuruJavaScriptInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BookSearchWebView : AppCompatActivity() {

    // private val URL = "http://10.0.2.2:5173/book-search"
    private val URL = "https://outsourcing-guru-book.vercel.app/book-search"
    private lateinit var webView: WebView

    lateinit var auth: FirebaseAuth

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_search_web_view)

        auth = Firebase.auth
        if(auth.currentUser == null){
            Toast.makeText(this, "로그인이 필요한 페이지입니다", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

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

        webView.addJavascriptInterface(
            GuruJavaScriptInterface(this, auth.currentUser!!.uid),
            "AndroidGuru2"
        )

        webView.loadUrl(URL)
    }
}
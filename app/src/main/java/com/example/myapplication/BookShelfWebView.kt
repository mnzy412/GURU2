package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.myapplication.utils.GuruJavaScriptInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BookShelfWebView : AppCompatActivity() {

    private val FILE_CHOOSER_RESULT_CODE = 100
    private var mUploadMessage: ValueCallback<Array<Uri>>? = null

    // private val URL = "http://10.0.2.2:5173/book-edit/"
    private val URL = "https://outsourcing-guru-book.vercel.app/book-edit/"
    private lateinit var webView: WebView

    lateinit var auth: FirebaseAuth

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_shelf_web_view)

        val bookshelfId = intent?.getStringExtra("bookshelf_id")
        if(bookshelfId == null){
            Toast.makeText(this, "올바르지 않은 접근입니다", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        auth = Firebase.auth
        if(auth.currentUser == null){
            Toast.makeText(this, "로그인이 필요한 페이지입니다", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        webView = findViewById(R.id.bookShelfWebView)

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

        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                mUploadMessage?.onReceiveValue(null)
                mUploadMessage = filePathCallback
                val intent = fileChooserParams?.createIntent()
                try {
                    startActivityForResult(intent!!, FILE_CHOOSER_RESULT_CODE)
                } catch (e: Exception) {
                    mUploadMessage = null
                    return false
                }
                return true
            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }

        webView.loadUrl(URL + bookshelfId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val result = if (data.data != null) arrayOf(data.data!!) else null
                mUploadMessage?.onReceiveValue(result)
                mUploadMessage = null
            } else {
                mUploadMessage?.onReceiveValue(null)
                mUploadMessage = null
            }
        }
    }
}
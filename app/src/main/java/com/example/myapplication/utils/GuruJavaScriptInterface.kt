package com.example.myapplication.utils

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface
import android.widget.Toast

class GuruJavaScriptInterface (
    private val activity: Activity,
    private val currentUserUid: String,
) {
    @JavascriptInterface
    fun getUserUid(): String{
        return currentUserUid
    }

    @JavascriptInterface
    fun close(){
        activity.finish()
    }

    @JavascriptInterface
    fun showToast(text: String){
        activity.runOnUiThread {
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
        }
    }

}
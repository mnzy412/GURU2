package com.example.myapplication.utils

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface

class GuruJavaScriptInterface (
    private val activity: Activity,
) {


    @JavascriptInterface
    fun close(){
        activity.finish()
    }
//
//    @JavascriptInterface
//    fun getUserPosition() {
//        log.d("getUserPosition")
//        var address = mainActivity.getAddress()
//
//        val gson: Gson? = Gson()
//        val point = Point(address[0], address[1])
//        val result = gson?.toJson(point)
//        log.d("getUserPosition : $result")
//        handler.post {
//            mainActivity.webView.loadUrl("javascript:window.apps.getUserPosition(`${result}`)")
//        }
//    }
//
//    @JavascriptInterface
//    fun getVersionName(): String {
//        log.d("getVersionName")
//        return BuildConfig.VERSION_NAME // ex) 2.2.8
//    }
//
//    @JavascriptInterface
//    fun downloadFiles(files: String) {
//        val gson = Gson()
//        try {
//            val files = gson.fromJson(files, Array<BobaFile>::class.java)
//            files.forEach { file ->
//                val request = DownloadManager.Request(Uri.parse(file.url))
//                    .setTitle(file.name)
//                    .setDescription("다운로드중입니다...")
//                    // 다운로드 진행중, 완료일 때 알림에 표시
//                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                    .setAllowedOverMetered(true)
//                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file.name)
//                downloadManager.enqueue(request)
//            }
//        } catch (e: JsonParseException) {
//            Toast.makeText(context, "파일 다운로드를 할 수 없습니다, JSON 변환에 실패했습니다.", Toast.LENGTH_SHORT).show()
//        }
//    }
}
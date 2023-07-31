package com.example.myapplication.model.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId

/**
 * This BookDTO works with Firebase Firestore databases.
 * (Native mode)
 */
data class BookshelfDTO(
    val id: String, // Firebase Firestore document id (auto-generated), Not a UUID type!!
    val authors: List<String>,
    val book_contents: String,
    val book_title: String,
    val isbn13: String,
    val published_at: String,
    val publisher: String,
    val user_id: String,
    val read_history: List<BookReadHistoryDTO>,
    val readtime: Long, // 총 읽은 시간 기록
    val thumbnail: String,
    val rating: Long
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun readOnDate(date: LocalDate): Boolean {
        val data =  read_history.any {
            val localDate = it.datetime.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            localDate.isEqual(date) && it.amount > 0
        }
        Log.d("BookshelfDTO", "readOnDate: $data")
        return data
    }
}

data class BookReadHistoryDTO(
    val amount: Long, // 당일 읽은 장 수 기록
    val datetime: Timestamp // 읽은 시간 기록
)

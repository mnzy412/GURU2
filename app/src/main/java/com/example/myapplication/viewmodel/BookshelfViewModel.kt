package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.data.BookReadHistoryDTO
import com.example.myapplication.model.data.BookshelfDTO
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookshelfViewModel : ViewModel() {

    val TAG = "BookshelfViewModel"

    private val userUid = Firebase.auth.currentUser!!.uid
    private val firestore = Firebase.firestore
    private val bookshelfData = MutableLiveData<List<BookshelfDTO>>()

    fun getBookshelfData(): LiveData<List<BookshelfDTO>> = bookshelfData

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchBookshelfData() {
        Log.d(TAG, "fetchBookshelfData")
        val list = mutableListOf<BookshelfDTO>()
        GlobalScope.launch(Dispatchers.IO) {
            val query =
                firestore.collection("bookshelf")
                    .whereEqualTo("user_id", userUid)
            query.get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val data = document.data
                    val bookshelf = BookshelfDTO(
                        id = document.id,
                        authors = data["authors"] as List<String>,
                        book_contents = data["book_contents"] as String,
                        book_title = data["book_title"] as String,
                        isbn13 = data["isbn13"] as String,
                        published_at = data["published_at"] as String,
                        publisher = data["publisher"] as String,
                        user_id = data["user_id"] as String,
                        read_history = (data["read_history"] as List<Map<String, Any?>>).map { history ->
                            BookReadHistoryDTO(
                                amount = history["amount"] as Long,
                                datetime = history["datetime"] as Timestamp
                            )
                        },
                        readtime = data["readtime"] as Long,
                        thumbnail = data["thumbnail"] as String,
                        rating = data["rating"] as Long
                    )
                    Log.d(TAG, "bookshelf: $bookshelf")
                    if (list.find { it.id == bookshelf.id } == null) {
                        list.add(bookshelf)
                    }
                }

                bookshelfData.postValue(list)
            }.addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        }
    }
}

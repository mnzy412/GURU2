package com.example.myapplication.record

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.myapplication.R

class SearchActivity2 : AppCompatActivity() {

    private lateinit var record_backBtn : ImageView
    private lateinit var record_save : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search2)

        record_backBtn = findViewById(R.id.record_backBtn)
        record_backBtn.setOnClickListener {    //이전으로
            var intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        record_save = findViewById(R.id.record_save)
        record_save.setOnClickListener {
            var intent = Intent(this, BookAddActivity::class.java)
            startActivity(intent)
        }

    }
}
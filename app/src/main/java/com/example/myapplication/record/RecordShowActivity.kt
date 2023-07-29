package com.example.myapplication.record

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.myapplication.R

class RecordShowActivity : AppCompatActivity() {

    private lateinit var record_backBtn : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_show)

        record_backBtn = findViewById(R.id.record_backBtn)
        record_backBtn.setOnClickListener {    //이전으로
            var intent = Intent(this, BookAddActivity::class.java)
            startActivity(intent)
        }
    }
}
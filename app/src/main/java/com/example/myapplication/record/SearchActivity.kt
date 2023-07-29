package com.example.myapplication.record

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R

class SearchActivity : AppCompatActivity() {

    private lateinit var record_save : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        record_save = findViewById(R.id.record_save)
        record_save.setOnClickListener {
            var intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }


}
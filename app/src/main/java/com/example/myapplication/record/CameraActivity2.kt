package com.example.myapplication.record

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.myapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CameraActivity2 : AppCompatActivity() {

    lateinit var record_backBtn : ImageView
    lateinit var record_save :ImageView
    lateinit var textBtn : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera2)

        record_backBtn = findViewById(R.id.record_backBtn)
        record_backBtn.setOnClickListener {    //이전으로
            var intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        record_save = findViewById(R.id.record_save)
        record_save.setOnClickListener {    //기록하기로
            var intent = Intent(this, BookAddActivity::class.java)
            startActivity(intent)
        }

        textBtn = findViewById(R.id.textBtn) //FloatActionButton 눌렀을 때 다른 액티비티로 이동
        textBtn.setOnClickListener {
            val intent = Intent(this, AddTextActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.example.myapplication.record

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import com.example.myapplication.R

class AddTextActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var record_backBtn : ImageButton
    private lateinit var record_save :ImageView
    private lateinit var editText: EditText
    private var offsetX = 0
    private var offsetY = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_text)

        //imageView = findViewById(R.id.recording_save)
        editText = findViewById(R.id.editText)

        editText.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // 드래그 시작 시 초기 좌표를 저장합니다.
                        offsetX = event.rawX.toInt() - view.x.toInt()
                        offsetY = event.rawY.toInt() - view.y.toInt()
                    }
                    MotionEvent.ACTION_MOVE -> {
                        // 드래그 중일 때 뷰를 이동시킵니다.
                        view.x = event.rawX - offsetX
                        view.y = event.rawY - offsetY
                    }
                }
                return true
            }
        })

        imageView = findViewById(R.id.recording_save)

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

    }

}
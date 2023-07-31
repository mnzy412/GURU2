package com.example.myapplication.record

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.R

class CameraActivity : AppCompatActivity() {

    lateinit var bitmap: Bitmap
    lateinit var photo: ImageView
    lateinit var record_save : ImageView
    lateinit var record_backBtn : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        //객체 생성
        photo = findViewById(R.id.photo)
        val CameraBtn: Button = findViewById(R.id.CameraBtn)

        //버튼 이벤트
        CameraBtn.setOnClickListener {
            //사진 촬영
            val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            activityResult.launch(intent)
        }

        record_save = findViewById(R.id.record_save)
        record_save.setOnClickListener {    //사진첨부 실행
            var intent = Intent(this, CameraActivity2::class.java)
            startActivity(intent)
        }

        record_backBtn = findViewById(R.id.record_backBtn)
        record_backBtn.setOnClickListener {    //이전으로
            var intent = Intent(this, BookAddActivity::class.java)
            startActivity(intent)
        }

    }//OnCreate

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == RESULT_OK && it.data != null)
            {
                val extras = it.data!!.extras

                bitmap = extras?.get("data") as Bitmap

                photo.setImageBitmap(bitmap)
            }
    }


}
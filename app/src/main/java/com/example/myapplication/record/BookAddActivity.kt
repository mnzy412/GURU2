package com.example.myapplication.record

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R



class BookAddActivity : AppCompatActivity() {

    private lateinit var titleEt: EditText
    private lateinit var review: EditText
    private lateinit var imgbtn: Button
    private lateinit var micbtn : ImageButton
    private lateinit var textCount :TextView
    private lateinit var record_backBtn : ImageView
    private lateinit var record_save :ImageView
    private lateinit var bookSearch : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_add)

        micbtn = findViewById(R.id.micbtn)
        micbtn.setOnClickListener {    //음성기록으로 이동
            var intent = Intent(this, RecordingActivity::class.java)
            startActivity(intent)
        }

        textCount = findViewById(R.id.textCount)
        review = findViewById(R.id.review)
        review.addTextChangedListener(object: TextWatcher{  //글자 수 세기

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                textCount.text = "0 / 200"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userinput = review.text.toString()
                textCount.text = userinput.length.toString() + " / 200"
            }

            override fun afterTextChanged(s: Editable?) {
                var userinput = review.text.toString()
                textCount.text = userinput.length.toString() + " / 200"
            }

        })

        imgbtn = findViewById(R.id.imgbtn)
        imgbtn.setOnClickListener {    //사진첨부 실행
            var intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        record_backBtn = findViewById(R.id.record_backBtn)
        record_backBtn.setOnClickListener {    //이전으로
            var intent = Intent(this, RecordFragment::class.java)
            startActivity(intent)
        }

        record_save = findViewById(R.id.record_save)
        record_save.setOnClickListener {
            var intent = Intent(this, RecordShowActivity::class.java)
            startActivity(intent)
        }

        bookSearch = findViewById(R.id.bookSearch) //책 검색으로 이동
        bookSearch.setOnClickListener {
            var intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}
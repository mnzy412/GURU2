package com.example.myapplication.mypage

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.widget.Button
class Mypage_quit: AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage_quit)

        val deleteBtn = findViewById<Button>(R.id.btn_delete)

        deleteBtn.setOnClickListener {

            val name = editTxtdelname.text.toString().trim()
            val dbHelper = DBHelper.getInstance(this, "member.db",)
            dbHelper.delete(name)
            finish()

        }
    }
}
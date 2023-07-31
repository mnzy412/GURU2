package com.example.myapplication.mypage

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.Signup2Activity
import com.google.firebase.database.DatabaseReference

class Mypage_quit: AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.mypage_quit)
    }

//    val deleteBtn = findViewById<Button>(R.id.btn_delete)
//        deleteBtn.setOnClickListener {
//
//        val nickname = viewBinding.nicknameEt.text.toString()
//        val email = intent.getStringExtra("email").toString()
//        val password = intent.getStringExtra("password").toString()
//
//        val signup2Activity = Signup2Activity.getInstance(this, "mDbRef.db",)
//
//        dbHelper.delete(nickname)
//        dbHelper.delete(email)
//        dbHelper.delete(password)
//
//        }
}

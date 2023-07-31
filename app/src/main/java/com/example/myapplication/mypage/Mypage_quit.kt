package com.example.myapplication.mypage

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.LoginActivity
import com.example.myapplication.Signup2Activity
import com.example.myapplication.User
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.MypageQuitBinding
import com.google.firebase.database.DatabaseReference

class Mypage_quit: AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    lateinit var btn_delete : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewBinding = MypageQuitBinding.inflate(layoutInflater)

        btn_delete = findViewById(R.id.btn_delete)

        btn_delete.setOnClickListener{
            mAuth.signOut()
            startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }



}
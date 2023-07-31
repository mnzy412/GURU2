package com.example.myapplication.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.R
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth

class MypagePwChangeActivity : AppCompatActivity() {

    lateinit var emailEt : EditText
    lateinit var sendBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_pw_change)

        emailEt = findViewById(R.id.emailEt)
        val sendBtn: Button = findViewById(R.id.sendBtn)

        sendBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()

            if (email.isNotEmpty()) {
                sendPasswordResetEmail(email)
            }

        }
    }

    private fun sendPasswordResetEmail(email: String) {
        val auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 비밀번호 재설정 이메일이 성공적으로 보내졌을 때 처리할 코드
                    println("비밀번호 재설정 이메일을 성공적으로 보냈습니다.")
                } else {
                    // 실패한 경우 처리할 코드
                    println("비밀번호 재설정 이메일 전송에 실패했습니다.")
                }
            }
    }
}
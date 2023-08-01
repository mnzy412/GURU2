package com.example.myapplication.mypage

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.myapplication.R


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MypagePwChangeActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var loginBtn : Button
    private lateinit var emailEt : EditText
    private lateinit var pwdEt : EditText
    lateinit var quit_close : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_pw_change)

        quit_close = findViewById(R.id.quit_close)
        quit_close.setOnClickListener {    //이전 화면으로 돌아가기
            var intent = Intent(this, MypageFragment::class.java)
            startActivity(intent)
        }

        loginBtn = findViewById(R.id.loginBtn)
        emailEt = findViewById(R.id.emailEt)
        pwdEt = findViewById(R.id.pwdEt)

        loginBtn.setOnClickListener {
            // EditText 필드에서 이메일과 비밀번호 가져오기
            val email = emailEt.text.toString().trim()
            val password = pwdEt.text.toString()

            // login 함수 호출
            login(email, password)
        }

    }

        private fun login(email: String, password: String) {
            val auth = FirebaseAuth.getInstance()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 로그인 성공
                        val user = auth.currentUser
                        user?.let {
                            // 비밀번호 재설정을 위해 새 비밀번호를 설정
                            val newPassword = "newPassword123" // 새로운 비밀번호를 입력해주세요.
                            updatePassword(it, newPassword)
                        }
                    } else {
                        // 로그인 실패
                        showToast("로그인에 실패했습니다.")
                    }
                }
        }

        private fun updatePassword(user: FirebaseUser, newPassword: String) {
            user.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 비밀번호 업데이트 성공
                        showToast("비밀번호를 성공적으로 업데이트했습니다.")
                        // 비밀번호 업데이트 후, 비밀번호 재입력 화면으로 이동
                        val intent = Intent(this, MyPagePwChangeActivity2::class.java)
                        intent.putExtra("newPassword", newPassword)
                        startActivity(intent)
                    } else {
                        // 실패한 경우 처리할 코드
                        showToast("비밀번호 업데이트에 실패했습니다.")
                    }
                }
        }

        private fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
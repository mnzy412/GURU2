package com.example.myapplication.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MyPagePwChangeActivity2 : AppCompatActivity() {

    private lateinit var newPassword: String
    private lateinit var pwdEt: EditText
    private lateinit var pwdEt2: EditText
    private lateinit var confirmBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_pw_change2)

        newPassword = intent.getStringExtra("newPassword") ?: ""
        pwdEt = findViewById(R.id.pwdEt)
        pwdEt2 = findViewById(R.id.pwdEt2)
        confirmBtn = findViewById(R.id.confirmBtn)

        confirmBtn.setOnClickListener {
            val password1 = pwdEt.text.toString()
            val password2 = pwdEt2.text.toString()

            if (password1.isEmpty() || password2.isEmpty()) {
                showToast("새 비밀번호를 입력해주세요.")
                return@setOnClickListener
            }

            if (password1 != password2) {
                showToast("비밀번호가 일치하지 않습니다.")
                return@setOnClickListener
            }

            // 두 번 입력한 새 비밀번호가 일치하는 경우, 비밀번호 업데이트 수행
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                updatePassword(it, password1)
            }
        }
    }

    private fun updatePassword(user: FirebaseUser, newPassword: String) {
        user.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 비밀번호 업데이트 성공
                    showToast("비밀번호가 성공적으로 변경되었습니다.")
                    finish()
                } else {
                    // 실패한 경우 처리할 코드
                    showToast("비밀번호 변경에 실패했습니다.")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
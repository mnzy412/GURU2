package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignupBinding.inflate(layoutInflater)

        viewBinding.registerBackBtn.setOnClickListener {    //이전 화면으로 돌아가기
            finish()
        }

        viewBinding.signupNextBtn.setOnClickListener { //인증번호 입력 화면 이동
            val intent = Intent (this, Signup2Activity::class.java)

            intent.putExtra("email", viewBinding.emailEt.text.toString().trim());

            val password = viewBinding.pwdEt.text.toString().trim()
            intent.putExtra("password", viewBinding.pwdEt.text.toString().trim());

            Log.d("SignupActivity", "Password: ${password}")

            this.startActivity(intent)
            overridePendingTransition(0, 0) //화면 전환 효과 없애기

            finish()
        }

        setContentView(viewBinding.root)
    }
}
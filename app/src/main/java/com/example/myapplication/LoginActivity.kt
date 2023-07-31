package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityLoginBinding
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityLoginBinding.inflate(layoutInflater)

        mAuth = Firebase.auth

        //로그인 완료
        viewBinding.loginBtn.setOnClickListener{
            val email = viewBinding.emailEt.text.toString()
            val password = viewBinding.pwdEt.text.toString()

            login(email, password)
        }

        //회원가입으로 이동
        viewBinding.registerBtn.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            this.startActivity(intent)
            overridePendingTransition(0, 0) //화면 전환 효과 없애기
        }

        setContentView(viewBinding.root)
    }

    // 로그아웃하지 않을 시 자동 로그인 , 회원가입시 바로 로그인 됨
    public override fun onStart() {
        super.onStart()
        moveMainPage(mAuth?.currentUser)
    }

    // 유저정보 넘겨주고 메인 액티비티 호출
    fun moveMainPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }


    //로그인 함수
    private fun login(email:String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 로그인 성공
                    overridePendingTransition(0, 0) //화면 전환 효과 없애기
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT,).show()
                    moveMainPage(mAuth?.currentUser)
                    finish()

                } else {
                    // 로그인 실패
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // 잘못된 비밀번호
                        Toast.makeText(this, "잘못된 비밀번호입니다.", Toast.LENGTH_SHORT).show()
                    } else if (task.exception is FirebaseAuthInvalidUserException) {
                        // 사용자가 존재하지 않거나 다른 로그인 방법을 사용하고 있음
                        Toast.makeText(this, "유효하지 않은 사용자 또는 로그인 방법입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        // 일반적인 로그인 오류
                        Toast.makeText(this, "로그인에 실패했습니다. 나중에 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    Log.d("Login", "Error: ${task.exception}")
                }
            }
    }
}
package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databinding.ActivitySignup2Binding
import com.example.myapplication.databinding.ActivitySignupBinding
import com.example.myapplication.mypage.MypagePwChangeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Signup2Activity : AppCompatActivity() {

    lateinit var viewBinding: ActivitySignup2Binding

    lateinit var mAuth: FirebaseAuth

    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignup2Binding.inflate(layoutInflater)



        //인증 초기화
        mAuth = Firebase.auth

        //db 초기화
        mDbRef = Firebase.database.reference

        viewBinding.signupFinBtn.setOnClickListener {
            val nickname = viewBinding.nicknameEt.text.toString()
            val email = intent.getStringExtra("email").toString()
            val password = intent.getStringExtra("password").toString()

            Log.d("Signup2Activity", "nickname: ${nickname}")
            Log.d("Signup2Activity", "password: ${password}")

            signup(nickname, email, password)

        }

        viewBinding.registerBackBtn.setOnClickListener {    //이전 화면으로 돌아가기
            finish()
        }

        setContentView(viewBinding.root)
    }

    private fun addUserToDatabase(nickname: String,email: String, uId: String){
        mDbRef.child("user").child(uId).setValue(User(nickname,email,uId))
    }


    //회원가입
    private fun signup(nickname:String, email:String, password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {  //회원가입 성공
                    val nickname = viewBinding.nicknameEt.text.toString()
                    val intent = Intent (this, LoginActivity::class.java)
                    overridePendingTransition(0, 0) //화면 전환 효과 없애기
                    startActivity(intent)
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT,).show()
                    addUserToDatabase(nickname,email, mAuth.currentUser?.uid!!)

                } else {  //회원가입 실패
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT,).show()

                    Log.d("register","Error: ${task.exception}")

                }
            }
    }



}


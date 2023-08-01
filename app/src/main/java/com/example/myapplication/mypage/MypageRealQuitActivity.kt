package com.example.myapplication.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.LoginActivity
import com.example.myapplication.databinding.ActivityMypageRealQuitBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MypageRealQuitActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMypageRealQuitBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewBinding = ActivityMypageRealQuitBinding.inflate(layoutInflater)
        // FirebaseAuth 인스턴스 초기화
        auth = FirebaseAuth.getInstance()

        setContentView(viewBinding.root)

        viewBinding.quitClose.setOnClickListener() {
            val intent = Intent(this, MypageFragment::class.java)
            startActivity(intent)
            finish() // 현재 액티비티를 종료하여 이전 액티비티로 돌아가는 효과를 낼 수 있습니다.
        }


        viewBinding.deleteBtn.setOnClickListener {
            val email = viewBinding.emailEt.text.toString()
            val password = viewBinding.pwdEt.text.toString()

            // 사용자를 현재 로그인된 사용자로 가져옵니다.
            val user: FirebaseUser? = auth.currentUser

            // 이메일과 비밀번호로 사용자를 재인증합니다.
            val credential = EmailAuthProvider.getCredential(email, password)
            user?.reauthenticate(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 재인증 성공
                    Toast.makeText(this, "재인증에 성공했습니다.", Toast.LENGTH_SHORT).show()

                    val user: FirebaseUser? = auth.currentUser

                    // AlertDialog를 통해 한 번 더 확인받습니다.
                    AlertDialog.Builder(this)
                        .setTitle("회원탈퇴")
                        .setMessage("정말로 회원탈퇴 하시겠습니까?")
                        .setPositiveButton("탈퇴") { _, _ ->
                            user?.delete()?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // 회원탈퇴 성공
                                    Toast.makeText(this, "회원탈퇴에 성공했습니다.", Toast.LENGTH_SHORT).show()
                                    var intent=Intent(this, LoginActivity::class.java) //로그인 페이지 이동
                                    startActivity(intent)
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    finish()
                                } else {
                                    // 회원탈퇴 실패
                                    Toast.makeText(this, "회원탈퇴에 실패했습니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        .setNegativeButton("취소", null)
                        .show()
                } else {
                    // 재인증 실패
                    Toast.makeText(this, "재인증에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }
}



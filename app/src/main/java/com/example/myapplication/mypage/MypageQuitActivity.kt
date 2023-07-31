package com.example.myapplication.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.databinding.ActivityMypageQuitBinding
import com.google.firebase.database.DatabaseReference

class MypageQuitActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivityMypageQuitBinding
    lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    lateinit var btn_delete : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var viewBinding = ActivityMypageQuitBinding.inflate(layoutInflater)

       viewBinding.quitClose.setOnClickListener() {
           val intent = Intent(this, MypageFragment::class.java)
           startActivity(intent)
           finish() // 현재 액티비티를 종료하여 이전 액티비티로 돌아가는 효과를 낼 수 있습니다.
       }

        viewBinding.btnNext.setOnClickListener{
            val intent = Intent(this, MypageRealQuitActivity::class.java)
            this.startActivity(intent)
            overridePendingTransition(0, 0) //화면 전환 효과 없애기
        }


        setContentView(viewBinding.root)
    }



}
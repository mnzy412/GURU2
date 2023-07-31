package com.example.myapplication.mypage

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R
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
           finish()
       }

        btn_delete.setOnClickListener{
            mAuth.signOut()
            startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }



}
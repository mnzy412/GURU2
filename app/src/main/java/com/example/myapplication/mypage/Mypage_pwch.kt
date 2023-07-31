//package com.example.myapplication.mypage
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.myapplication.R
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//
//class Mypage_pwch : AppCompatActivity() {
//    lateinit var mAuth: FirebaseAuth
//    private lateinit var mDbRef: DatabaseReference
//    lateinit var btn_pwchange : Button
//    private var changePasswordMode =false
//    override fun OnCrate(savedInstanceState:Bundle?){
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.mypage_changepw)
//
//        btn_pwchange = findViewById(R.id.btn_pwchange)
//
//        btn_pwchange.setOnClickListener{
//            val passwordPre = intent.getStringExtra("pw_ch").toString()
//            val passwordLa = intent.getStringExtra("pw_re").toString()
//            if(passwordPre == passwordLa)
//            {
//                var editTextNewPassword = EditText(this)
//                editTextNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
//                changePassword(editTextNewPassword.text.toString())
//
//            }
//            else
//            {
//                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
//            }
//    }
//
//    fun changePassword(password:String){
//        FirebaseAuth.getInstance() .currentUser!!.updatePassword(password).addOnCompleteListener { task ->
//            if(task.isSuccessful){
//                Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show()
//            }else{
//                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
//
//            }
//        }
//    }
//}
//
//}
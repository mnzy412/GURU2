package com.example.myapplication.record

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.R
import java.util.ArrayList
import java.util.Locale

class RecordingActivity : AppCompatActivity() {

    lateinit var resultText : TextView
    lateinit var record_backBtn : ImageButton
    lateinit var recording_save : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recording)



        record_backBtn = findViewById(R.id.record_backBtn)
        record_backBtn.setOnClickListener {    //이전 화면으로 돌아가기
            var intent = Intent(this, BookAddActivity::class.java)
            startActivity(intent)
        }
        //초기화
        resultText = findViewById(R.id.result_text)

        //음성전환 버튼 이벤트
        val speakImage : ImageView = findViewById(R.id.speak_image)
        speakImage.setOnClickListener {
            val intent : Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            //음성 인식기에 사용되는 음성모델 정보 설정
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

            //음성 인식기에 인식되는 언어 설정
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.KOREA)

            if(intent.resolveActivity(packageManager) != null){
                //음성 -> 텍스트 전환
            } else {
                Toast.makeText(this, "당신의 장비가 음성을 텍스트로 변경하지 못합니다.",
                    Toast.LENGTH_SHORT).show()
            }
        }


    }//onCreate

    private val activityResult : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK && it.data != null){
            val result: ArrayList<String> = it.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    as ArrayList<String>
            //음성 -> 텍스트에 보여주기
            resultText.text = result[0]

        }
    }
}
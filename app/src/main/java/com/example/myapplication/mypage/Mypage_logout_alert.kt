//package com.example.myapplication.mypage
//
//class Mypage_logout_alert : AppCompatActivity() {
//        // view
//        private lateinit var resultText: TextView
//        private lateinit var alertBtn: Button
//        private lateinit var listBtn: Button
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            setContentView(R.layout.activity_alert_dialog)
//
//            // View inflate
//            resultText = findViewById(R.id.resultText)
//            alertBtn = findViewById(R.id.alertDialogButton)
//            listBtn = findViewById(R.id.listDialogButton)
//
//            // 기본 형태의 다이얼로그
//            alertBtn.setOnClickListener {
//                // 다이얼로그를 생성하기 위해 Builder 클래스 생성자를 이용해 줍니다.
//                val builder = AlertDialog.Builder(this)
//                builder.setTitle("타이틀 입니다.")
//                    .setMessage("메세지 내용 부분 입니다.")
//                    .setPositiveButton("확인",
//                        DialogInterface.OnClickListener { dialog, id ->
//                            resultText.text = "확인 클릭"
//                        })
//                    .setNegativeButton("취소",
//                        DialogInterface.OnClickListener { dialog, id ->
//                            resultText.text = "취소 클릭"
//                        })
//                // 다이얼로그를 띄워주기
//                builder.show()
//            }
//
//    }
//}
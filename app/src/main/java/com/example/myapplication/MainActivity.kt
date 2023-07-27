package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.book.BookFragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.mypage.MypageFragment
import com.example.myapplication.record.RecordFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        viewBinding.bottomNavi.run {

            //바텀 네비게이션 선택 이벤트
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.bottom_book -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.frameFragment.id, BookFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.bottom_record -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.frameFragment.id, RecordFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.bottom_mypage -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.frameFragment.id, MypageFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }

            //초기 설정
            selectedItemId = R.id.bottom_book //처음 실행할 때 책장 메뉴 가르킬 수 있도록 함
        }


    }


}
package com.example.myapplication.mypage

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.LoginActivity
import com.example.myapplication.MypageRVAdpater
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


@Suppress("UNREACHABLE_CODE")
class MypageFragment : Fragment() {
    private lateinit var viewBinding: FragmentMypageBinding

    lateinit var mypage_btn_quit: Button
    lateinit var mypage_btn_change: Button
    lateinit var mypage_btn_logout: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? {
        viewBinding = FragmentMypageBinding.inflate(layoutInflater)

        val mypageList: ArrayList<Mypage> = arrayListOf()
        val mAdapter = MypageRVAdpater(mypageList)

        mypageList.apply {
            add(
                Mypage(
                    "보관된 책",
                    3,
                    "권"
                )
            )
            add(
                Mypage(
                    "완독한 책",
                    27,
                    "권"
                )
            )
            add(
                Mypage(
                    "독서 시간",
                    375,
                    "시간"
                )
            )
            add(
                Mypage(
                    "메모",
                    32,
                    "개"
                )
            )
        }

        viewBinding.mypageRv.layoutManager = GridLayoutManager (context,2)
        viewBinding.mypageRv.adapter = mAdapter


        mypage_btn_logout = findViewbyId(R.id.mypage_btn_logout)
        mypage_btn_change = findViewbyId(R.id.mypage_btn_change)
        mypage_btn_quit = findViewbyId(R.id.mypage_btn_quit)

        //로그아웃 버튼 클릭 시 다이얼로그창 띄우기
        mypage_btn_logout.setOnClickListener{view ->
            var dialog = AlertDialog.Builder(this)
            dialog.setTitle("로그아웃")
            dialog.setMessage("Booktory를 정말 로그아웃하시겠습니까?")
            dialog.setIcon(R.drawable.mypage_profill)

            FirebaseAuth.getInstance().signOut()

            fun toast_p() {
                Toast.makeText(view.context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
            var dialog_listener = object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when(which){
                        DialogInterface.BUTTON_POSITIVE ->
                            toast_p()
                    }
                }
            }
            dialog.setPositiveButton("네",dialog_listener)
            dialog.setNegativeButton("아니오",null)
            dialog.show()
        }
        mypage_btn_change.setOnClickListener{

            val intent = Intent(this, Mypage_pwch::class.java)
            startActivity(intent)

        }
        mypage_btn_quit.setOnClickListener{

            val intent = Intent(this, Mypage_quit::class.java)
            startActivity(intent)

        }

//        mAdapter!!.itemClick = object : MypageRVAdpater.ItemClick {
//
//        }
        return viewBinding.root

    }

    private fun Intent(mypageFragment: MypageFragment, java: Class<Mypage_quit>): Intent? {

    }

}


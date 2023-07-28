package com.example.myapplication.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MypageRVAdpater
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMypageBinding

@Suppress("UNREACHABLE_CODE")
class MypageFragment : Fragment() {
    private lateinit var viewBinding: FragmentMypageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
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


//        mAdapter!!.itemClick = object : MypageRVAdpater.ItemClick {
//
//        }
        return viewBinding.root

    }
}


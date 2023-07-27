package com.example.myapplication.book

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBookBinding

class BookFragment : Fragment() {
    private lateinit var viewBinding: FragmentBookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBookBinding.inflate(layoutInflater)

        //검색 버튼 클릭 이벤트 처리
//        viewBinding.searchBtn.setOnClickListener {
//
//        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager
            .beginTransaction()
            .replace(viewBinding.tabBookContainer.id, BookTotalFragment())
            .commitAllowingStateLoss()
    }


}
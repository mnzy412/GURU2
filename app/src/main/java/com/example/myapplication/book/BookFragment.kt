package com.example.myapplication.book

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.BookSearchWebView
import com.example.myapplication.book.adapter.HomeViewPagerAdapter
import com.example.myapplication.databinding.FragmentBookBinding
import com.google.android.material.tabs.TabLayoutMediator

class BookFragment : Fragment() {

    private lateinit var binding: FragmentBookBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookBinding.inflate(layoutInflater)

        // 책 검색 버튼
        binding.searchBtn.setOnClickListener {
            val intent = Intent(context, BookSearchWebView::class.java)
            startActivity(intent)
        }

        // TabLayout 및 ViewPager 초기화
        binding.viewPager.adapter = HomeViewPagerAdapter(requireActivity())
        binding.viewPager.isUserInputEnabled = false // ViewPager Swiping 비활성화

        // ViewPager2 and TabLayout 연동
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "전체" else "캘린더"
        }.attach()

        // TabItem 간 간격 조정
        for (i in 0 until binding.tabLayout.tabCount) {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, 0, 16, 0)
            tab.requestLayout()
        }

        return binding.root
    }
}
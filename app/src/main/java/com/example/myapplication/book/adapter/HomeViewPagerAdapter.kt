package com.example.myapplication.book.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.book.pages.BookGridFragment
import com.example.myapplication.book.pages.CalendarFragment

class HomeViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val list = listOf(BookGridFragment(), CalendarFragment())

    override fun getItemCount(): Int = list.count()

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

}
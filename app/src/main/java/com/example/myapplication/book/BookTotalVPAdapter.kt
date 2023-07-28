package com.example.myapplication.book

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class BookTotalVPAdapter(bookFragment: FragmentActivity) : FragmentStateAdapter(bookFragment)  {

    val fragments: List<Fragment>
    init {
        fragments = listOf(Ttl1Fragment(),Ttl2Fragment(),Ttl3Fragment())
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
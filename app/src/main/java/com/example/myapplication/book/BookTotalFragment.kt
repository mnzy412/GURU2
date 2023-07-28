package com.example.myapplication.book

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBookTotalBinding
import com.google.android.material.tabs.TabLayoutMediator


class BookTotalFragment : Fragment() {
   private lateinit var viewBinding: FragmentBookTotalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       viewBinding = FragmentBookTotalBinding.inflate(layoutInflater)
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookTotalVPAdapter = BookTotalVPAdapter(requireActivity())
        viewBinding.vpBookTotal.adapter = bookTotalVPAdapter

        TabLayoutMediator(viewBinding.dotsLayout!!, viewBinding.vpBookTotal!!){tab, position ->
            viewBinding.vpBookTotal.setCurrentItem(tab.position)
        }.attach()


    }
}
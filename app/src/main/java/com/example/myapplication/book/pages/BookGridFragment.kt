package com.example.myapplication.book.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.myapplication.R
import com.example.myapplication.book.adapter.BookGridViewAdapter
import com.example.myapplication.databinding.FragmentBookBinding
import com.example.myapplication.model.data.BookDTO

class BookGridFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_bookgrid, container, false)

        val sampleDataList = listOf(
            BookDTO("https://image.dongascience.com/Photo/2020/06/d7cdc8c0033067f76a66eec382a540e0.jpg", 20),
            BookDTO("https://image.dongascience.com/Photo/2020/06/d7cdc8c0033067f76a66eec382a540e0.jpg", 50),
            BookDTO("https://www.dailyvet.co.kr/wp-content/uploads/2022/08/220804bibong1.jpg", 70),
            BookDTO("https://www.dailyvet.co.kr/wp-content/uploads/2022/08/220804bibong1.jpg", 10),
            BookDTO("https://www.dailyvet.co.kr/wp-content/uploads/2022/08/220804bibong1.jpg", 90),
            BookDTO("https://www.dailyvet.co.kr/wp-content/uploads/2022/08/220804bibong1.jpg", 30),
            BookDTO("https://www.dailyvet.co.kr/wp-content/uploads/2022/08/220804bibong1.jpg", 80),
            BookDTO("https://www.dailyvet.co.kr/wp-content/uploads/2022/08/220804bibong1.jpg", 40),
            BookDTO("https://www.dailyvet.co.kr/wp-content/uploads/2022/08/220804bibong1.jpg", 60),
            BookDTO("https://www.dailyvet.co.kr/wp-content/uploads/2022/08/220804bibong1.jpg", 100)
        )

        val gridView = binding.findViewById<GridView>(R.id.home_book_gridview)
        val adapter = BookGridViewAdapter(binding.context, sampleDataList)
        gridView?.adapter = adapter

        return binding
    }

}
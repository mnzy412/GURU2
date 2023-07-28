package com.example.myapplication.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.myapplication.databinding.ItemTotalBinding

class TotalGVAdapter(private val items: MutableList<Total>, private var context: Context): BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Total = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var viewBinding = ItemTotalBinding.inflate(LayoutInflater.from(parent?.context), parent, false)

        val item: Total = items[position]
        viewBinding.bookImg.setImageResource(item.image)


        return viewBinding.root
    }
}
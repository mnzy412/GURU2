package com.example.myapplication.book.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.data.BookDTO

class BookGridViewAdapter(private val context: Context, private val books: List<BookDTO>) : BaseAdapter() {

    override fun getCount(): Int {
        return books.size
    }

    override fun getItem(position: Int): Any {
        return books[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.home_gridview_item, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val book = books[position]

        Glide.with(context).load(book.bookImageURL).into(holder.bookImage)

        if (book.bookReadProgress == 100) {
            holder.gradientViewGroup.visibility = View.GONE
        }else{
            holder.bookProgress.text = "${book.bookReadProgress}%"
        }

        return view!!
    }

    private class ViewHolder(view: View) {
        val bookImage: ImageView = view.findViewById(R.id.book_image)
        val bookProgress: TextView = view.findViewById(R.id.book_read_progress)
        val gradientViewGroup: View = view.findViewById(R.id.gradation_viewgroup)
    }
}

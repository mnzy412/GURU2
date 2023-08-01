package com.example.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.myapplication.BookShelfWebView
import com.example.myapplication.R
import com.example.myapplication.model.data.BookshelfDTO

class BookshelfGridViewAdapter(
    val context: Context,
    private val data: List<BookshelfDTO>
) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
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

        val book = data[position]

        Glide.with(context).load(book.thumbnail).into(holder.bookImage)
        holder.gradientViewGroup.visibility = View.GONE

        view?.setOnClickListener {
            val bookshelf: BookshelfDTO = data[position]
            val intent = Intent(context, BookShelfWebView::class.java)
            intent.putExtra("bookshelf_id", bookshelf.id)
            context.startActivity(intent)
        }

        return view!!
    }

    private class ViewHolder(view: View) {
        val bookImage: ImageView = view.findViewById(R.id.book_image)
        val bookProgress: TextView = view.findViewById(R.id.book_read_progress)
        val gradientViewGroup: View = view.findViewById(R.id.gradation_viewgroup)
    }

}

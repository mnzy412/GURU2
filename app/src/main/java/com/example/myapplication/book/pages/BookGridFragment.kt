package com.example.myapplication.book.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.example.myapplication.BookShelfWebView
import com.example.myapplication.R
import com.example.myapplication.adapter.BookshelfGridViewAdapter
import com.example.myapplication.viewmodel.BookshelfViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.ViewModelProvider

class BookGridFragment : Fragment() {

    // Firebase auth current userUid
    private lateinit var userUid: String
    private lateinit var firestore: FirebaseFirestore

    private lateinit var bookshelfViewModel: BookshelfViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_bookgrid, container, false)

        bookshelfViewModel = activity?.run {
            ViewModelProvider(this)[BookshelfViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        userUid = Firebase.auth.currentUser!!.uid
        firestore = Firebase.firestore

        val gridView = binding.findViewById<GridView>(R.id.home_book_gridview)

        bookshelfViewModel.getBookshelfData().observe(viewLifecycleOwner) { bookshelfList ->
            Log.d("BookGridFragment", "bookshelfList: ${bookshelfList.size}")
            gridView.adapter = BookshelfGridViewAdapter(binding.context, bookshelfList)
            gridView.deferNotifyDataSetChanged()
        }

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val bookshelf = bookshelfViewModel.getBookshelfData().value?.get(position)
            if (bookshelf == null) {
                Toast.makeText(binding.context, "책장 정보를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                return@OnItemClickListener
            }
            val intent = Intent(context, BookShelfWebView::class.java)
            intent.putExtra("bookshelf_id", bookshelf.id)
            startActivity(intent)
        }

        return binding
    }

    override fun onResume() {
        super.onResume()
        bookshelfViewModel.fetchBookshelfData()
    }
}
package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.myapplication.adapter.BookshelfGridViewAdapter
import com.example.myapplication.book.viewmodel.BookshelfViewModel
import com.example.myapplication.databinding.ActivityCalendarDetailBinding
import com.example.myapplication.model.data.BookshelfDTO
import java.time.LocalDate
import java.time.ZoneId

class CalendarDetailActivity : AppCompatActivity() {

    private val viewModel: BookshelfViewModel by viewModels()

    // binding
    private lateinit var binding: ActivityCalendarDetailBinding
    private lateinit var date: LocalDate

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchBookshelfData()

        val year = intent.getIntExtra("year", 0)
        val month = intent.getIntExtra("month", 0)
        val day = intent.getIntExtra("day", 0)

        if (year == 0 || month == 0 || day == 0) {
            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // set toolbar
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolBar.title = "${month}월 ${day}일에 읽은 책"

        date = LocalDate.of(year, month, day)
        viewModel.getBookshelfData().observe(this) { bookshelfList ->
            setupAdapter(bookshelfList)
        }

        setupAdapter(viewModel.getBookshelfData().value ?: emptyList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAdapter(bookshelfList: List<BookshelfDTO>) {
        Log.d("CalendarDetailActivity", "input ${bookshelfList.size}")
        val list: List<BookshelfDTO> = getBookListByDate(bookshelfList, date)
        Log.d("CalendarDetailActivity", list.toString())
        binding.homeBookGridview.adapter = BookshelfGridViewAdapter(this, list)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getBookListByDate(list: List<BookshelfDTO>, date: LocalDate): List<BookshelfDTO> {
        return list.filter { book ->
            book.read_history.any {
                val localDate =
                    it.datetime.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                localDate.isEqual(date) && it.amount > 0
            }
        }.map { book ->
            book.copy(
                read_history = book.read_history.filter {
                    val localDate = it.datetime.toDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    localDate.isEqual(date)
                },
                readtime = book.read_history.filter {
                    val localDate = it.datetime.toDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    localDate.isEqual(date)
                }.sumOf { it.amount }
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}


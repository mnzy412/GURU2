package com.example.myapplication.book.pages

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.myapplication.CalendarDetailActivity
import com.example.myapplication.R
import com.example.myapplication.book.viewmodel.BookshelfViewModel
import com.example.myapplication.databinding.FragmentCalendarBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private val viewModel: BookshelfViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(layoutInflater)

        setupDayBinder()
        setupMonthBinder()

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        val firstDayOfWeek = firstDayOfWeekFromLocale()
        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.SUNDAY)

        binding.calendarView.setup(startMonth, endMonth, daysOfWeek.first())
        binding.calendarView.scrollToMonth(currentMonth)

        binding.calendarView.monthScrollListener = { month ->

            val yearMonth = month.yearMonth
            val year = yearMonth.year
            val monthKo = yearMonth.month.getDisplayName(TextStyle.FULL, Locale.KOREA)

            val title = "${year}년 $monthKo"
            binding.monthYearTextView.text = title
        }


        viewModel.getBookshelfData().observe(viewLifecycleOwner) { bookshelfList ->
            updateDayBinder()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchBookshelfData()
    }

    private fun setupDayBinder() {
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
            }
        }
    }

    private fun setupMonthBinder() {
        binding.calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)

                @RequiresApi(Build.VERSION_CODES.O)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    container.titlesContainer.children.map { it as TextView }
                        .forEachIndexed { index, textView ->
                            val dayOfWeek = daysOfWeek()[index]
                            val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREA)
                            textView.text = title
                        }
                }
            }
    }

    private fun updateDayBinder() {
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                updateDayView(container, day)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDayView(container: DayViewContainer, day: CalendarDay) {

        container.view.setOnClickListener {
            val intent = Intent(requireContext(), CalendarDetailActivity::class.java)
            intent.putExtra("year", day.date.year)
            intent.putExtra("month", day.date.month.value)
            intent.putExtra("day", day.date.dayOfMonth)
            startActivity(intent)
        }

        container.textView.text = day.date.dayOfMonth.toString()

        viewModel.getBookshelfData().value?.firstOrNull { it.readOnDate(day.date) }?.let {
            Glide.with(requireContext()).load(it.thumbnail).into(container.imageView)
        } ?: container.imageView.setImageDrawable(null)

        container.textView.setTextColor(
            if (day.position == DayPosition.MonthDate) Color.BLACK
            else resources.getColor(R.color.tab_select_text)
        )
    }
}

/**
 * ViewHolder
 */
class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.calendarDayText)

    val imageView = view.findViewById<ImageView>(R.id.calendarDayImageView)

    // With ViewBinding
    // val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
}

class MonthViewContainer(view: View) : ViewContainer(view) {
    val titlesContainer = view as ViewGroup
}


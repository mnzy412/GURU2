package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.myapplication.adapter.PickerAdapter
import com.example.myapplication.adapter.PickerLayoutManager
import com.example.myapplication.databinding.ActivityCalendarDetailBinding
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.utils.ScreenUtils

class CalendarDetailActivity : AppCompatActivity() {

    private val data = (1..10).toList()
        .map { it.toString() } as ArrayList<String>
    private lateinit var rvHorizontalPicker: RecyclerView
    private lateinit var sliderAdapter: PickerAdapter

    // binding
//    private lateinit var binding: ActivityCalendarDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_detail)

        val month = intent.getIntExtra("month", 1)
        val day = intent.getIntExtra("day", 1)

        setPicker()

        /*
        * for the below picker
        * */
//        numberPicker.value = 12
//        numberPicker.minValue = 0
//        numberPicker.setMaxValue(24)
    }

    private fun setPicker() {
        rvHorizontalPicker = findViewById(R.id.rv_horizontal_picker)

        // Setting the padding such that the items will appear in the middle of the screen
        val padding: Int = ScreenUtils.getScreenWidth(this) / 2 - ScreenUtils.dpToPx(this, 40)
        rvHorizontalPicker.setPadding(padding, 0, padding, 0)

        // Setting layout manager
        rvHorizontalPicker.layoutManager = PickerLayoutManager(this).apply {
            callback = object : PickerLayoutManager.OnItemSelectedListener {
                override fun onItemSelected(layoutPosition: Int) {
                    sliderAdapter.setSelectedItem(layoutPosition)
                    Log.d("selected text", data[layoutPosition])
                    Toast.makeText(this@CalendarDetailActivity, data[layoutPosition], Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        // Setting Adapter
        sliderAdapter = PickerAdapter()
        rvHorizontalPicker.adapter = sliderAdapter.apply {
            setData(data)
            callback = object : PickerAdapter.Callback {
                override fun onItemClicked(view: View) {
                    rvHorizontalPicker.smoothScrollToPosition(
                        rvHorizontalPicker.getChildLayoutPosition(
                            view
                        )
                    )
                }
            }
        }
    }
}


package com.example.myapplication.record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListItemRecord1Binding
import com.example.myapplication.databinding.ListItemRecord2Binding
import com.example.myapplication.record.Const.HASIMAGE
import com.example.myapplication.record.Const.NOIMAGE

class RecordRVAdapter(private val recordList: ArrayList<Record>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var itemClick: RecordRVAdapter.ItemClick? = null

    inner class RecordWithImageViewHolder(val viewBinding: ListItemRecord1Binding) :
            RecyclerView.ViewHolder(viewBinding.root) {
                fun bind(record: Record) {
                    viewBinding.img.setImageResource(record.img!!)
                    viewBinding.bookTitle.text = record.title
                    viewBinding.userName.text = record.userName
                }
            }

    inner class RecordWithoutImageViewHolder(val viewBinding2: ListItemRecord2Binding) :
        RecyclerView.ViewHolder(viewBinding2.root) {
        fun bind(record: Record) {
            viewBinding2.content.text = record.content
            viewBinding2.bookTitle.text = record.title
            viewBinding2.userName.text = record.userName
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (recordList[position].hasImage==HasImage.TRUE) HASIMAGE else NOIMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HASIMAGE) {
            val view = ListItemRecord1Binding.inflate(LayoutInflater.from(parent.context),parent, false)
            RecordWithImageViewHolder(view)
        } else {
            val view = ListItemRecord2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
            RecordWithoutImageViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == HASIMAGE) {
            (holder as RecordWithImageViewHolder).bind(recordList[position])
        }else {
            (holder as RecordWithoutImageViewHolder).bind(recordList[position])
        }

        if (itemClick != null) {
            if (recordList[position].hasImage == HasImage.TRUE) {
                (holder as RecordRVAdapter.RecordWithImageViewHolder).viewBinding!!.recordItem.setOnClickListener(
                    View.OnClickListener {
                        itemClick?.onClick(it, position)
                    })
            }  else {
                (holder as RecordRVAdapter.RecordWithoutImageViewHolder).viewBinding2!!.recordItem.setOnClickListener(
                    View.OnClickListener {
                        itemClick?.onClick(it, position)
                    })
            }
        }
    }

    override fun getItemCount(): Int = recordList.size

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

}

object Const {
    const val HASIMAGE = 0
    const val NOIMAGE = 1
}
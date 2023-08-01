package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListItemMypageBinding
import com.example.myapplication.mypage.Mypage


class MypageRVAdpater(private val mypageList: List<Mypage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemClick: MypageRVAdpater.ItemClick? = null

    inner class MypageViewHolder(val viewBinding: ListItemMypageBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(mypage: Mypage) {
            viewBinding.titleTv.text = mypage.title
            if(mypage.title == "독서 시간"){
                viewBinding.numTv.text = formatSecondsToHMS(mypage.num)
            }else{
                viewBinding.numTv.text = mypage.num.toString()
            }
            viewBinding.subTv.text = mypage.sub
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ListItemMypageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MypageViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MypageRVAdpater.MypageViewHolder).bind(mypageList[position])
    }

    override fun getItemCount(): Int = mypageList.size

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    private fun formatSecondsToHMS(seconds: Int): String {
        val h = seconds / 3600
        val m = (seconds % 3600) / 60
        val s = seconds % 60

        return buildString {
            if (h > 0) append("${h}시 ")
            if (m > 0 || h > 0) append("${m}분 ")
            append("${s}초")
        }
    }


}
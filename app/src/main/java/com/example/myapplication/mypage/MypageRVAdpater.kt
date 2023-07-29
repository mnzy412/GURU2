package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListItemMypageBinding
import com.example.myapplication.mypage.Mypage


class MypageRVAdpater (private val mypageList: ArrayList<Mypage>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var itemClick: MypageRVAdpater.ItemClick? = null


    inner class MypageViewHolder(val viewBinding:ListItemMypageBinding):
            RecyclerView.ViewHolder(viewBinding.root) {
            fun bind(mypage: Mypage) {
                viewBinding.titleTv.text = mypage.title
                viewBinding.numTv.text = mypage.num.toString()
                viewBinding.subTv.text = mypage.sub
            }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ListItemMypageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MypageViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MypageRVAdpater.MypageViewHolder).bind(mypageList[position])
    }

    override fun getItemCount(): Int = mypageList.size

    interface ItemClick {
        fun onClick (view: View, position: Int)
    }


}
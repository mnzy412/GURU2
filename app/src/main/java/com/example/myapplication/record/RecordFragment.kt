
package com.example.myapplication.record

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication.MypageRVAdpater
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRecordBinding
import com.example.myapplication.mypage.Mypage

class RecordFragment : Fragment() {
    lateinit var viewBinding: FragmentRecordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRecordBinding.inflate(layoutInflater)


        val recordList: ArrayList<Record> = arrayListOf()
        val rAdapter = RecordRVAdapter(recordList)

        recordList.apply {
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.TRUE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    R.drawable.img
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.TRUE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    R.drawable.img
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.TRUE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    R.drawable.img
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.TRUE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    R.drawable.img
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )
            add(
                Record(
                    HasImage.FALSE,
                    "김별",
                    "놀고싶어요밖에서",
                    "밖에서 놀 수 있는 것은 최고의 행복이다.",
                    null
                )
            )

        }

        viewBinding.recordRV.layoutManager = StaggeredGridLayoutManager (2,LinearLayoutManager.VERTICAL)
        viewBinding.recordRV.adapter = rAdapter



        return viewBinding.root
    }
}
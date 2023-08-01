package com.example.myapplication.record

import android.content.Intent
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class RecordFragment : Fragment() {
    lateinit var viewBinding: FragmentRecordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val AddRecordBtn: FloatingActionButton = view.findViewById(R.id.AddRecordBtn) //기록 추가 버튼 누르면 액티비티로 이동

        AddRecordBtn.setOnClickListener {
            val intent = Intent(requireContext(), BookAddActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRecordBinding.inflate(layoutInflater)

        // Firestore에서 데이터를 가져와서 RecyclerView에 표시합니다.
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("memo")
            .get()
            .addOnSuccessListener { result ->
                val recordList = ArrayList<Record>()
                for (document in result) {
                    val hasImage = document.getBoolean("hasImage") ?: false
                    val title = document.getString("title") ?: ""
                    val userName = document.getString("userName") ?: ""
                    val content = document.getString("description") ?: ""
                    val imgResId = document.getLong("imgResId")?.toInt() ?: -1

                    recordList.add(Record(if (hasImage) HasImage.TRUE else HasImage.FALSE, userName, title, content, if (imgResId != -1) imgResId else null))
                }

                setupRecyclerView(recordList)
            }
            .addOnFailureListener { exception ->
                // 데이터 가져오기 실패 시 처리
            }

        return viewBinding.root
    }

    private fun setupRecyclerView(recordList: ArrayList<Record>) {
        val rAdapter = RecordRVAdapter(recordList)

        viewBinding.recordRV.layoutManager = StaggeredGridLayoutManager (2, LinearLayoutManager.VERTICAL)
        viewBinding.recordRV.adapter = rAdapter
    }
}

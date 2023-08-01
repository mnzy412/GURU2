package com.example.myapplication.mypage

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.myapplication.LoginActivity
import com.example.myapplication.MypageRVAdpater
import com.example.myapplication.User
import com.example.myapplication.viewmodel.BookshelfViewModel
import com.example.myapplication.databinding.FragmentMypageBinding
import com.example.myapplication.model.data.BookshelfDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class MyPageData(
    var bookShelfCount: Int, // 보관된 책 수
    var bookReadCount: Int, // 완독한 책 수
    var memoCount: Int, // 메모 수
    var bookReadTimeAmount: Double // 독서 총합
)

class MypageFragment : Fragment() {
    private lateinit var viewBinding: FragmentMypageBinding
    private lateinit var Auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var database: DatabaseReference // realtime db

    private lateinit var bookshelfViewModel: BookshelfViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        viewBinding = FragmentMypageBinding.inflate(layoutInflater)
        Auth = Firebase.auth
        firestore = Firebase.firestore
        database = Firebase.database.reference

        bookshelfViewModel = activity?.run {
            ViewModelProvider(this)[BookshelfViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        setUserNickname()

        val myPageData = MyPageData(0, 0, 0, 0.0)

        viewBinding.mypageRv.layoutManager = GridLayoutManager(context, 2)
        viewBinding.mypageRv.addItemDecoration(GridSpacingItemDecoration(2, 24))

        CoroutineScope(Dispatchers.IO).launch {
            val list = bookshelfViewModel.getBookshelfData().value ?: emptyList()
            myPageData.bookShelfCount = list.size
            myPageData.memoCount = getMyMemoCount()
            myPageData.bookReadTimeAmount = getTotalReadTime(list)

            val myPageList = mutableListOf<Mypage>().apply {
                add(Mypage("보관된 책", myPageData.bookShelfCount, "권"))
                add(Mypage("완독한 책", myPageData.bookReadCount, "권"))
                add(Mypage("독서 시간", myPageData.bookReadTimeAmount.toInt(), ""))
                add(Mypage("기록", myPageData.memoCount, "개"))
            }
            val mAdapter = MypageRVAdpater(myPageList)

            CoroutineScope(Dispatchers.Main).launch {
                viewBinding.mypageRv.adapter = mAdapter
            }
        }

        viewBinding.mypageBtnLogout.setOnClickListener {
            // TODO: 사용자 확인 다이얼로그 필요
            Firebase.auth.signOut()
            val intent = Intent(context, LoginActivity::class.java) //로그인 페이지 이동
            startActivity(intent)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
//        mAdapter!!.itemClick = object : MypageRVAdpater.ItemClick {
//
//        }

        viewBinding.mypageBtnLogout.setOnClickListener(){
            showLogoutConfirmationDialog()

        }

        viewBinding.mypageBtnQuit.setOnClickListener() {
            var intent=Intent(activity, MypageQuitActivity::class.java) //로그인 페이지 이동
            startActivity(intent)
        }
        viewBinding.mypageBtnChange.setOnClickListener() {
            var intent=Intent(activity, MypagePwChangeActivity::class.java)
            startActivity(intent)
        }


        return viewBinding.root

    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("로그아웃")
            .setMessage("로그아웃 하시겠습니까?")
            .setPositiveButton("로그아웃") { _, _ ->
                // 사용자가 확인 버튼을 누른 경우 로그아웃 처리
                Firebase.auth.signOut()
                val intent = Intent(activity, LoginActivity::class.java) //로그인 페이지 이동
                startActivity(intent)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            }
            .setNegativeButton("취소", null)
            .show()
    }


    private fun getTotalReadTime(bookshelfList: List<BookshelfDTO>): Double {
        return bookshelfList.sumOf { it.readtime.toDouble() }
    }


    private suspend fun getMyMemoCount(): Int {
        return try {
            val query = firestore.collection("memo").whereEqualTo("user_id", Auth.currentUser!!.uid)
            val documents = query.get().await()
            documents.size()
        } catch (e: Error) {
            // TODO: Sentry Error Logging
            0
        }
    }

    private fun setUserNickname() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = database.child("user").child(Auth.currentUser!!.uid).get().await()
                CoroutineScope(Dispatchers.Main).launch {
                    viewBinding.mypageMain.text = data.getValue<User>()?.nickname ?: ""
                }
            } catch (e: Error) {
                throw e
            }
        }

    }

}

class GridSpacingItemDecoration(
    private val spanCount: Int, // Grid의 column 수
    private val spacing: Int // 간격
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)

        if (position >= 0) {
            val column = position % spanCount // item column
            outRect.apply {
                // spacing - column * ((1f / spanCount) * spacing)
                left = spacing - column * spacing / spanCount
                // (column + 1) * ((1f / spanCount) * spacing)
                right = (column + 1) * spacing / spanCount
                if (position < spanCount) top = spacing
                bottom = spacing
            }
        } else {
            outRect.apply {
                left = 0
                right = 0
                top = 0
                bottom = 0
            }
        }
    }
}


package com.example.myapplication.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MypageFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    var UserList = arrayListOf<User>(

        User("보관된 책","korean-otter@naver.com"),
        User("완독한 책","jmssk11@naver.com"),
        User("읽은 시간","hello@gmail.com"),
        User("물고기","asdf@naver.com")
    )
    //배열을 만들어 사용자의 정보를 담아준다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_mypage)

        var Adapter = ListAdapter(this, UserList)
        item_list.adapter = Adapter
        //만들어둔 ListAdapter를 이용해 카드뷰 - 사용자 정보 - 리스트뷰를 연결시켜준다.
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MypageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
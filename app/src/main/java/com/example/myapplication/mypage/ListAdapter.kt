package com.example.myapplication.mypage

import android.content.Context
import android.icu.text.CaseMap.Title
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListAdapter (val context: Context, val UserList: ArrayList<User>) : BaseAdapter() {
        override fun getCount(): Int {
            return UserList.size
        }

        override fun getItem(position: Int): Any {
            return UserList[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view : View = LayoutInflater.from(context).inflate(R.layout.item_list, null)
            val title = view.findViewById<TextView>(R.id.title_tv)
            val sub= view.findViewById<TextView>(R.id.sub_tv)
            //profile, name, email, content 각 변수를 만들어 item_user.xml의 각 정보를 담을 곳의 위치를 가지게 한다.

            val user = UserList[position]
            //user 변수에 배열(또는 서버에서 받아온 데이터)에 담긴 profile, name, email, content 정보를 담아준다.

            title.text = user.name
            sub.text = user.email
            //위에서 가져온 profile, name, email, content 각각의 변수를 만들어둔 카드뷰에 연결시켜준다.

            return view
            //연결이 완료된 뷰를 돌려준다.
        }
}
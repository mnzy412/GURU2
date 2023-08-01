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
import com.example.myapplication.record.BookAddActivity
import com.example.myapplication.record.HasImage
import com.example.myapplication.record.Record
import com.example.myapplication.record.RecordRVAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class RecordFragment : Fragment() {
    private lateinit var viewBinding: FragmentRecordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val AddRecordBtn: FloatingActionButton = view.findViewById(R.id.AddRecordBtn) //기록 추가 버튼 누르면 액티비티로 이동

        AddRecordBtn.setOnClickListener {
            val intent = Intent(requireContext(), BookAddActivity::class.java)
            startActivity(intent)
        }

        // Firebase 인증 객체를 사용하여 현재 사용자의 UID를 가져옵니다.
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        // userid를 가져온 후에 해당 userid에 해당하는 닉네임을 호출합니다.
        if (userId != null) {
            getNicknameByUserId(userId) { nickname ->
                // 닉네임을 호출한 후에 RecyclerView를 초기화합니다.
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
                            val imgResId = document.getLong("img_url")?.toInt() ?: -1

                            recordList.add(Record(if (hasImage) HasImage.TRUE else HasImage.FALSE, userName, title, content, if (imgResId != -1) imgResId else null))
                        }

                        setupRecyclerView(recordList)
                    }
                    .addOnFailureListener { exception ->
                        // 데이터 가져오기 실패 시 처리
                    }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentRecordBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    // Firebase Realtime Database에서 해당 userid에 해당하는 닉네임을 호출하는 함수
    private fun getNicknameByUserId(userId: String, callback: (String?) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users")

        userRef.child(userId).child("nickname").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val nickname = dataSnapshot.getValue(String::class.java)
                callback(nickname)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 호출에 실패한 경우 null을 콜백으로 전달합니다.
                callback(null)
            }
        })
    }

    private fun setupRecyclerView(recordList: ArrayList<Record>) {
        val rAdapter = RecordRVAdapter(recordList)

        viewBinding.recordRV.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        viewBinding.recordRV.adapter = rAdapter
    }
}

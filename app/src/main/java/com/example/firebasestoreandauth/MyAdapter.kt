package com.example.firebasestoreandauth

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasestoreandauth.databinding.ItemLayoutBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyAdapter(private val db: FirebaseFirestore, private val navigate: NavController, private val viewModel: MyViewModel) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var storage = Firebase.storage

    inner class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) {
            val item = viewModel.items[pos]
            val postId = item.postId
            val whoPosted = item.whoPosted
            var likes = item.likes.toInt()
            var liked = false
            // 프로필 사진 옆 유저 아이디 표시
            binding.userId.text = whoPosted
            // 좋아요 수를 표시
            binding.showLikes.text = "좋아요 " + likes + "개"

            binding.likeBtn.setOnClickListener {
                if (it.isSelected) {
                    likes -= 1
                    it.setBackgroundResource(R.drawable.icons8__96)
                }
                else {
                    likes += 1
                    viewModel.items[pos].likes = likes
                    it.setBackgroundResource(R.drawable.full_heart)
                }
                it.isSelected = !it.isSelected
                db.collection("PostInfo").document(postId).update("likes", likes)
                binding.showLikes.text = "좋아요 " + likes + "개"
            }

            binding.commentBtn.setOnClickListener {
                //viewModel.setUser(postedUser)
                //viewModel.setPostId(postedUser)
                viewModel.setPos(pos)
                //println("#$#$#$$#setpos" + viewModel.getPos())
                viewModel.ClickedPostInfo(item.postId)
                navigate.navigate(R.id.action_postFragment_to_commentFragment)
            }

            var commentsTest = ArrayList<Map<String, String>>()
            commentsTest.add(mapOf("test" to "hello"))

            val itemMap = hashMapOf(
                "comments" to commentsTest,
                "img" to "gs://sns-pbl.appspot.com/상상부기 2.png",
                "likes" to 1 as Number,
                "whoPosted" to "testing"
            )

            binding.uid.text = item.whoPosted + " "
            binding.postTitle.text = item.comments[0][whoPosted]

            val profileImageRef = storage.getReferenceFromUrl(item.profile_img)

            profileImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                binding.profileImg.setImageBitmap(bmp)
            }.addOnFailureListener {

            }

            val imageRef = storage.getReferenceFromUrl(item.postImgUrl)

            imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                binding.postImg.setImageBitmap(bmp)
            }.addOnFailureListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount() = viewModel.itemsSize
}
package com.example.developertest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.developertest.R
import com.example.developertest.models.Post
import kotlinx.android.synthetic.main.adapter_post.view.*

class PostsAdapter( private val postList: List<Post>,
                    private val context: Context) : Adapter<PostsAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList[position]
        holder?.let {
            it.bindView(post)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(post: Post) {
            val title = itemView.adapter_post_title
            val body = itemView.adapter_post_body

            title.text = post.title
            body.text = post.body
        }
    }
}
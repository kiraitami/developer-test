package com.example.developertest.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.developertest.R
import com.example.developertest.activities.PostsActivity
import com.example.developertest.models.User
import com.example.developertest.strings.USER_ID
import com.example.developertest.strings.USER_NAME
import kotlinx.android.synthetic.main.adapter_user.view.*

class UsersAdapter( private val userList: List<User>,
                    private val context: Context ) : Adapter<UsersAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]

        holder?.let {
            it.bindView(user)
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(context, PostsActivity::class.java)
            intent.putExtra(USER_ID, user.id)
            intent.putExtra(USER_NAME, user.name)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(user: User){
            val name = itemView.adapter_user_name
            val id = itemView.adapter_user_id
            val email = itemView.adapter_user_email
            val company = itemView.adapter_user_company
            val city = itemView.adapter_user_city

            name.text = user.name
            id.text = user.id
            email.text = user.email
            city.text = user.address.city
            company.text = user.company.name
        }
    }
}
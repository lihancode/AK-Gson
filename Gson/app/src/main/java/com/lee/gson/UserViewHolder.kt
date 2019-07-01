package com.lee.gson

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.github_item.view.*

class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val userName = itemView.user_name
    val userId = itemView.user_id
    fun bindto(user:User){
        userName.text = user.login
        userId.text = user.id.toString()
    }
}
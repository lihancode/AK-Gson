package com.lee.gson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.github_item.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : AppCompatActivity(),AnkoLogger {

    lateinit var users :ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getButton.setOnClickListener {

            val url = "https://api.github.com/users"
            doAsync {
                val json = URL(url).readText()
                users = Gson().fromJson<ArrayList<User>>(
                   json,
                   object:TypeToken<ArrayList<User>>(){}.type
                )
                uiThread {
                    recycler.setHasFixedSize(true)
                    recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                    recycler.adapter =UserAdapter()
                }

            }
        }
    }


    inner class UserAdapter() : RecyclerView.Adapter<UserViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            return UserViewHolder(layoutInflater.inflate(R.layout.github_item,parent,false))
        }

        override fun getItemCount(): Int {
            return if(users == null) 0 else users.size
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val user = users[position]
            holder.bindto(user)
            holder.itemView.tag =position
        }


    }
    inner class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val userName = itemView.user_name
        val userId = itemView.user_id
        fun bindto(user:User){
            userName.text = user.login
            userId.text = user.id.toString()
        }
    }

}



data class User(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
)



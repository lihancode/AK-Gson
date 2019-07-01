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
                    recycler.adapter =UserAdapter(users)
                }

            }
        }
    }

}




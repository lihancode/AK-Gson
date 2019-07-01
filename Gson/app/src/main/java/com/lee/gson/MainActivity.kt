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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class MainActivity : AppCompatActivity(),AnkoLogger {

    lateinit var users :ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getButton.setOnClickListener {

            val url = "https://api.github.com/"
//            getDataWithAnkoGson(url)
            val retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(GitHubService::class.java)
            val users = service.getUser().enqueue(object : Callback<ArrayList<User>>{
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    t.stackTrace
                }
                override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                     response.body()?.let {
                         users = it
                         recycler.setHasFixedSize(true)
                         recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                         recycler.adapter = UserAdapter(users)
                    }
                }

            })

        }
    }

    private fun getDataWithAnkoGson(url: String) {
        doAsync {
            val json = URL(url).readText()
            users = Gson().fromJson<ArrayList<User>>(
                json,
                object : TypeToken<ArrayList<User>>() {}.type
            )
            uiThread {
                recycler.setHasFixedSize(true)
                recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                recycler.adapter = UserAdapter(users)
            }

        }
    }

}




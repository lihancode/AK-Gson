# AK-Gson
Android Kotlin get data by Gson , Retrofit , Anko

# dependencies 
    def anko_version ='0.10.8'
    implementation "org.jetbrains.anko:anko:$anko_version"

    def gson_version = '2.8.5'
    implementation "com.google.code.gson:gson:$gson_version"
    
    def retrofit = '2.6.0'
    implementation "com.squareup.retrofit2:retrofit:$retrofit"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit"
    
    
    
# Main - Gson

      doAsync {
            val json = URL(https://api.github.com/user).readText()
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

# Main - Retrofit

            val url = "https://api.github.com/"
            val retrofit = Retrofit.Builder()
                                   .baseUrl(url)
                                   .addConverterFactory(GsonConverterFactory.create())
                                    .build()
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
            
# Interface
 
    interface GitHubService {
    @GET("users")
    fun getUser(): Call<ArrayList<User>>
    }

            

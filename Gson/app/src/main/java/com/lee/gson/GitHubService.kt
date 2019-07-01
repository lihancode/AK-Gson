package com.lee.gson

import retrofit2.Call
import retrofit2.http.GET

interface GitHubService {

    @GET("users")
    fun getUser(): Call<ArrayList<User>>
}
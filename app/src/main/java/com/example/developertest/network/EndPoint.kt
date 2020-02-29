package com.example.developertest.network

import com.example.developertest.models.Picture
import com.example.developertest.models.Post
import com.example.developertest.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPoint {
    @GET("users")
    fun getListOfUsers(): Call<List<User>>

    @GET("photos")
    fun getListOfPictures(): Call<List<Picture>>

    @GET("posts")
    fun getUserPublications(@Query("userId") userId: String) : Call<List<Post>>
}
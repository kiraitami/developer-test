package com.example.developertest.network

import com.example.developertest.models.Picture
import com.example.developertest.models.Post
import com.example.developertest.models.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPoint {
    @GET("users")
    fun getListOfUsers(): Observable<List<User>>

    @GET("photos")
    fun getListOfPictures(): Observable<List<Picture>>

    @GET("posts")
    fun getUserPublications(@Query("userId") userId: String) : Observable<List<Post>>
}
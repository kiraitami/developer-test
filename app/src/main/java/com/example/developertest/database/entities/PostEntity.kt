package com.example.developertest.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.developertest.models.Post

@Entity(tableName = "posts")
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: String,
    val title: String,
    val body: String) {

    fun convertToPost() : Post {
        return Post(
            id = id.toString(),
            userId = userId,
            title = title,
            body = body)
    }
}
package com.example.developertest.models

import com.example.developertest.database.entities.PostEntity

data class Post(
    val userId: String,
    val id: String,
    val title: String,
    val body: String) {

    fun convertToPostEntity() : PostEntity {
        return PostEntity(
            id = id.toInt(),
            userId = userId,
            title = title,
            body = body)
    }
}
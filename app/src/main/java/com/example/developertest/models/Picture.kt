package com.example.developertest.models

import com.example.developertest.database.entities.PictureEntity

data class Picture (
    val albumId: String,
    val id: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String) {

    fun convertToPictureEntity() : PictureEntity {
        return PictureEntity(
            id = id.toInt(),
            albumId = albumId,
            title = title,
            url = url,
            thumbnailUrl = thumbnailUrl
        )
    }
}
package com.example.developertest.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.developertest.models.Picture

@Entity(tableName = "pictures")
data class PictureEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val albumId: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String ){

    fun convertToPicture() : Picture {
        return Picture(
            id = id.toString(),
            albumId = albumId,
            title = title,
            url = url,
            thumbnailUrl = thumbnailUrl
        )
    }
}
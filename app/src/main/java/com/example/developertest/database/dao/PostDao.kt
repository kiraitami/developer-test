package com.example.developertest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.developertest.database.entities.PostEntity
import io.reactivex.Flowable

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(vararg postEntity: PostEntity)

    @Query("SELECT * FROM posts WHERE userId = :userId")
    fun getPostsFromDb(userId: Int) : Flowable<List<PostEntity>>
}
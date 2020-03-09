package com.example.developertest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.developertest.database.entities.PictureEntity
import io.reactivex.Flowable

@Dao
interface PicturesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPicture(pictureEntity: PictureEntity)

    @Query("SELECT * FROM pictures")
    fun getPicturesFromDb() : Flowable<List<PictureEntity>>
}
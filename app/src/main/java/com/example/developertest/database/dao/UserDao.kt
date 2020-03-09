package com.example.developertest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.developertest.database.entities.UserEntity
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg userEntity: UserEntity)

    @Query("SELECT * FROM users")
    fun getUsersFromDb() : Flowable<List<UserEntity>>
}
package com.example.developertest.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.developertest.database.dao.PicturesDao
import com.example.developertest.database.dao.PostDao
import com.example.developertest.database.dao.UserDao
import com.example.developertest.database.entities.PictureEntity
import com.example.developertest.database.entities.PostEntity
import com.example.developertest.database.entities.UserEntity

@Database(entities = [UserEntity::class, PostEntity::class, PictureEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun postDao() : PostDao
    abstract fun pictureDao() : PicturesDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE as AppDatabase
        }
    }
}
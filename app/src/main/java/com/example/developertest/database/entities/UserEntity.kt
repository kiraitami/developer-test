package com.example.developertest.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.developertest.models.Address
import com.example.developertest.models.Company
import com.example.developertest.models.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    @Embedded
    val address: Address,
    val phone: String,
    val website: String,
    @Embedded
    val company: Company ) {

    fun convertToUser () : User {
        return User(
            id = id.toString(),
            name = name,
            username = username,
            email = email,
            address = address,
            phone = phone,
            website = website,
            company = company
        )
    }
}
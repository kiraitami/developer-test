package com.example.developertest.models

import com.example.developertest.database.entities.UserEntity

data class User (
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company) {

    fun convertToUserEntity() : UserEntity {
        return UserEntity(
            id = id.toInt(),
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
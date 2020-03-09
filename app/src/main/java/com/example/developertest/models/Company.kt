package com.example.developertest.models

import com.google.gson.annotations.SerializedName

data class Company (
    @SerializedName("name")
    val companyName: String,
    val catchPhrase: String,
    val bs: String
)
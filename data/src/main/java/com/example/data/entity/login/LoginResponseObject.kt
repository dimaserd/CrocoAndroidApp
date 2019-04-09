package com.example.data.entity.login

import com.google.gson.annotations.SerializedName

class LoginResponseObject(

    @SerializedName("Result")
    val result: String,

    @SerializedName("TokenId")
    val tokenId: String
)
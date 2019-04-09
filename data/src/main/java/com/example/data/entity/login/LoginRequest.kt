package com.example.data.entity.login

import com.google.gson.annotations.SerializedName

class LoginRequest(

    @SerializedName("Email")
    val email: String,

    @SerializedName("Password")
    val password: String,

    @SerializedName("RememberMe")
    val rememberMe: Boolean
)
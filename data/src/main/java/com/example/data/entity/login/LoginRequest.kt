package com.example.data.entity.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,

    val password: String,

    @SerializedName("Remem")
    val rememberMe: Boolean
)
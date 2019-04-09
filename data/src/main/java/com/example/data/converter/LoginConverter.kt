package com.example.data.converter

import com.example.data.entity.login.LoginRequest
import com.example.data.entity.login.LoginResponse
import com.example.domain.model.User

object UserConverter {

    fun toNetwork(email: String, password: String) = LoginRequest(email, password, true)

    // TODO
    fun fromNetwork(user: LoginResponse): User {
        return User("")
    }
}
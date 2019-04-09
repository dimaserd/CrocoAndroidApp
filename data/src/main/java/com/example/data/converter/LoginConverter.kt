package com.example.data.converter

import com.example.data.entity.login.LoginRequest

object LoginConverter {

    fun toNetwork(email: String, password: String) = LoginRequest(email, password, true)
}
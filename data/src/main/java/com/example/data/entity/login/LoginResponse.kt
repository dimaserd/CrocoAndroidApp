package com.example.data.entity.login

import com.google.gson.annotations.SerializedName

class LoginResponse(

    @SerializedName("ResponseObject")
    val responseObject: LoginResponseObject,

    @SerializedName("IsSucceeded")
    val isSucceeded: Boolean,

    @SerializedName("Message")
    val message: String
)
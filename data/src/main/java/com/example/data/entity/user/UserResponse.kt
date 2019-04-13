package com.example.data.entity.user

import com.google.gson.annotations.SerializedName

class UserResponse(

    @SerializedName("ResponseObject")
    val responseObject: UserResponseObject,

    @SerializedName("IsSucceeded")
    val isSucceeded: Boolean,

    @SerializedName("Message")
    val message: String
)

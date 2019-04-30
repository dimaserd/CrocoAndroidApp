package com.example.data.entity.avatar_upload

import com.google.gson.annotations.SerializedName

class AvatarUploadResponse(

    @SerializedName("IsSucceeded")
    val success: Boolean,

    @SerializedName("ResponseObject")
    val responseObject: IntArray
)

package com.example.data.entity.task

import com.google.gson.annotations.SerializedName

class TaskResponseAuthor(

    @SerializedName("Surname")
    val surname: String,

    @SerializedName("Patronymic")
    val patronymic: String,

    @SerializedName("Email")
    val email: String,

    @SerializedName("Name")
    val name: String,

    @SerializedName("AvatarFileId")
    val avatarFileId: Int,

    @SerializedName("Id")
    val id: String
)

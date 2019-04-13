package com.example.data.entity.user

import com.google.gson.annotations.SerializedName

class UserResponseObject(

    @SerializedName("Email")
    val email: String?,

    @SerializedName("AvatarFileId")
    val avatarFileId: Int?,

    @SerializedName("Name")
    val name: String,

    @SerializedName("BirthDate")
    val birthDate: String?,

    @SerializedName("Surname")
    val secondName: String?,

    @SerializedName("Patronymic")
    val thirdName: String?,

    @SerializedName("Sex")
    val sex: Boolean,

    @SerializedName("ObjectJson")
    val objectJson: String?,

    @SerializedName("PhoneNumber")
    val phoneNumber: String?
)
package com.example.data.entity.all_users

import com.google.gson.annotations.SerializedName
import java.util.*

class AllUsersResponseList(

    @SerializedName("UnConfirmedEmail")
    val unConfirmedEmail: String,

    @SerializedName("EmailConfirmed")
    val emailConfirmed: Boolean,

    @SerializedName("UserName")
    val userName: String,

    @SerializedName("PhoneNumber")
    val phoneNumber: String?,

    @SerializedName("PhoneNumberConfirmed")
    val phoneNumberConfirmed: Boolean,

    @SerializedName("Balance")
    val balance: Int?,

    @SerializedName("Sex")
    val sex: Boolean,

    @SerializedName("ObjectJson")
    val objectJson: String?,

    @SerializedName("BirthDate")
    val birthDate: String?,

    @SerializedName("DeActivated")
    val deactivated: Boolean,

    @SerializedName("Rights")
    val rights: List<String>?,

    @SerializedName("Surname")
    val secondName: String?,

    @SerializedName("Patronymic")
    val thirdName: String?,

    @SerializedName("Email")
    val email: String,

    @SerializedName("Name")
    val name: String,

    @SerializedName("AvatarFileId")
    val avatarFileId: Int,

    @SerializedName("Id")
    val id: String
)

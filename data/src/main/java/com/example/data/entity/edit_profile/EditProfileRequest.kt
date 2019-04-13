package com.example.data.entity.edit_profile

import com.google.gson.annotations.SerializedName
import java.util.*

class EditProfileRequest(

    @SerializedName("Name")
    val name: String,

    @SerializedName("BirthDate")
    val birthDate: Date?,

    @SerializedName("Surname")
    val secondName: String?,

    @SerializedName("Patronymic")
    val thirdName: String?,

    @SerializedName("Sex")
    val sex: Boolean,

    @SerializedName("ObjectJson")
    val objectJson: Any?,

    @SerializedName("PhoneNumber")
    val phoneNumber: String
)

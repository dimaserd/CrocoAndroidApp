package com.example.data.entity.all_users

import com.google.gson.annotations.SerializedName
import java.util.*

class AllUsersRequestRegistrationDate(

    @SerializedName("Min")
    val min: Date,

    @SerializedName("Max")
    val max: Date
)
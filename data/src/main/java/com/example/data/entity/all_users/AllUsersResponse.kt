package com.example.data.entity.all_users

import com.google.gson.annotations.SerializedName

class AllUsersResponse(

    @SerializedName("")
    val totalCount: Int,

    @SerializedName("List")
    val users: List<AllUsersResponseList>,

    @SerializedName("Count")
    val count: Int,

    @SerializedName("Offset")
    val offset: Int
)

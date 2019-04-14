package com.example.data.entity.all_users

import com.google.gson.annotations.SerializedName

class AllUsersRequest(

    @SerializedName("Q")
    val query: String?,

    @SerializedName("Deactivated")
    val deactivated: Boolean?,

    @SerializedName("RegistrationDate")
    val registrationDate: AllUsersRequestRegistrationDate?,

    @SerializedName("SearchSex")
    val searchSex: Boolean?,

    @SerializedName("Sex")
    val sex: Boolean?,

    @SerializedName("HasPurchases")
    val hasPurchases: Boolean?,

    @SerializedName("Count")
    val count: Int?,

    @SerializedName("Offset")
    val offset: Int?
)
package com.example.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class User(
    val firstName: String,
    val secondName: String?,
    val thirdName: String?,
    val birthDate: Date?,
    val sex: Sex?,
    val email: String?,
    val phoneNumber: String?
) : Parcelable

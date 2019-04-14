package com.example.data.converter

import com.example.data.entity.user.UserResponse
import com.example.domain.model.Sex
import com.example.domain.model.User
import com.example.domain.utils.exceptions.ConvertException
import java.text.SimpleDateFormat
import java.util.*

object UserConverter {

    fun fromNetwork(user: UserResponse?): User {
        user?.responseObject?.let {
            return User(
                firstName = it.name,
                secondName = if (it.secondName != "") it.secondName else null,
                thirdName = if (it.thirdName != "") it.thirdName else null,
                birthDate = if (it.birthDate != null) SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss",
                    Locale.ENGLISH
                ).parse(it.birthDate) else null,
                sex = if (it.sex) Sex.MALE else Sex.FEMALE,
                email = it.email,
                phoneNumber = it.phoneNumber
            )
        } ?: throw ConvertException("user is null")
    }
}

package com.example.data.converter

import com.example.data.entity.all_users.AllUsersRequest
import com.example.data.entity.all_users.AllUsersRequestRegistrationDate
import com.example.data.entity.all_users.AllUsersResponse
import com.example.data.entity.user.UserResponse
import com.example.domain.model.Sex
import com.example.domain.model.User
import com.example.domain.utils.exceptions.ConvertException
import java.text.SimpleDateFormat
import java.util.*

object AllUsersConverter {

    fun toNetwork(): AllUsersRequest {
        // TODO
        return AllUsersRequest(
            query = "",
            deactivated = null,
            registrationDate = null,
            searchSex = null,
            sex = null,
            hasPurchases = null,
            count = null,
            offset = null
        )
    }

    fun fromNetwork(usersResponse: AllUsersResponse?): List<User> {
        usersResponse?.let {
            return it.users.map { usersResponse ->
                with(usersResponse) {
                    User(
                        firstName = name,
                        secondName = secondName,
                        thirdName = thirdName,
                        email = email,
                        sex = if (sex) Sex.MALE else Sex.FEMALE,
                        birthDate = if (birthDate != null) SimpleDateFormat(
                            "yyyy-MM-dd'T'HH:mm:ss",
                            Locale.ENGLISH
                        ).parse(birthDate) else null,
                        phoneNumber = phoneNumber
                    )
                }
            }
        } ?: throw ConvertException("users are null")
    }
}

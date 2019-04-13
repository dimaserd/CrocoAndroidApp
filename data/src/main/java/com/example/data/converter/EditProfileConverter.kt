package com.example.data.converter

import com.example.data.entity.edit_profile.EditProfileRequest
import com.example.domain.model.Sex
import com.example.domain.model.User
import com.example.domain.utils.exceptions.ConvertException

object EditProfileConverter {

    fun toNetwork(user: User?): EditProfileRequest {
        user?.let {
            return with(user) {
                EditProfileRequest(
                    name = firstName,
                    birthDate = birthDate,
                    secondName = secondName,
                    thirdName = thirdName,
                    sex = sex == Sex.MALE,
                    objectJson = null,
                    phoneNumber = phoneNumber ?: ""
                )
            }
        } ?: throw ConvertException("user is null")
    }
}

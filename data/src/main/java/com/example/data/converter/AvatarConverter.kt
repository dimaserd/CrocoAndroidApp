package com.example.data.converter

import android.graphics.BitmapFactory
import com.example.data.entity.avatar.AvatarResponse
import com.example.domain.model.Avatar
import com.example.domain.utils.exceptions.ConvertException

object AvatarConverter {

    fun fromNetwork(avatar: AvatarResponse?): Avatar {
        avatar?.let {
            with(avatar.responseBody.bytes()) {
                val bitmap = BitmapFactory.decodeByteArray(this, 0, this.size)
                return Avatar(bitmap)
            }
        } ?: throw ConvertException("avatar is null")
    }
}

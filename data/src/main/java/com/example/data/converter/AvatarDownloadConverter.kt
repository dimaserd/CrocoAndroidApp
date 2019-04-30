package com.example.data.converter

import android.graphics.BitmapFactory
import com.example.data.entity.avatar_download.AvatarDownloadResponse
import com.example.domain.model.Avatar
import com.example.domain.utils.exceptions.ConvertException

object AvatarDownloadConverter {

    fun fromNetwork(avatarDownload: AvatarDownloadResponse?): Avatar {
        avatarDownload?.let {
            with(avatarDownload.responseBody.bytes()) {
                val bitmap = BitmapFactory.decodeByteArray(this, 0, this.size)
                return Avatar(bitmap)
            }
        } ?: throw ConvertException("avatarDownload is null")
    }
}

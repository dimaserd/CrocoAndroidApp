package com.example.data.converter

import android.net.Uri
import com.example.data.entity.avatar_upload.AvatarUploadResponse
import com.example.domain.utils.exceptions.ConvertException
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object AvatarUploadConverter {

    fun toNetwork(avatarUri: Uri): MultipartBody.Part {
        val file = File(avatarUri.path)
        val body = RequestBody.create(MediaType.parse("image/jpeg"), file)
        return MultipartBody.Part.createFormData("image", file.name, body)
    }

    fun fromNetwork(avatarUploadResponse: AvatarUploadResponse?): Int {
        avatarUploadResponse?.let {
            return it.responseObject[0]
        } ?: throw ConvertException("avatar upload response is null")
    }
}

package com.example.data.network

import com.example.data.entity.avatar_upload.AvatarUploadResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PhotoApi {

    @Streaming
    @GET("/FileCopies/Images/Icon/{avatarFieldId}.jpg")
    fun downloadAvatar(@Path("avatarFieldId") avatarFieldId: Int): Single<ResponseBody>

    @Multipart
    @POST("/Api/FilesDirectory/Upload")
    fun uploadAvatar(@Part file: MultipartBody.Part): Call<AvatarUploadResponse>
}

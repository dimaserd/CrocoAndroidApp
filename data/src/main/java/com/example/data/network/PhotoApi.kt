package com.example.data.network

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Streaming

interface PhotoApi {

    @Streaming
    @GET("/FileCopies/Images/Icon/{avatarFieldId}.jpg")
    fun downloadAvatar(@Path("avatarFieldId") avatarFieldId: Int): Single<ResponseBody>

    @Streaming
    @POST("/FileCopies/Images/Icon/{avatarFieldId}.jpg")
    fun uploadAvatar(
        @Path("avatarFieldId") avatarFieldId: Int
        // TODO
    ): Completable
}

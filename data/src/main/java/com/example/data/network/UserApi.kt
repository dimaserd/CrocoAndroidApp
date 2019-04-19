package com.example.data.network

import com.example.data.entity.all_users.AllUsersRequest
import com.example.data.entity.all_users.AllUsersResponse
import com.example.data.entity.edit_profile.EditProfileRequest
import com.example.data.entity.user.UserResponse
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

interface UserApi {

    @GET("/Api/Client/Get")
    fun loadUser(): Single<UserResponse>

    @Streaming
    @GET("/FileCopies/Images/Icon/{avatarFieldId}.jpg")
    fun loadAvatar(@Path("avatarFieldId") avatarFieldId: Int): Single<ResponseBody>

    @POST("/Api/User/Get")
    fun loadAllUsers(@Body body: AllUsersRequest): Single<AllUsersResponse>

    @POST("/Api/Client/Update")
    fun updateProfile(@Body body: EditProfileRequest): Completable
}

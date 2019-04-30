package com.example.data.network

import com.example.data.entity.all_users.AllUsersRequest
import com.example.data.entity.all_users.AllUsersResponse
import com.example.data.entity.edit_profile.EditProfileRequest
import com.example.data.entity.user.UserResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("/Api/Client/Get")
    fun loadUser(): Single<UserResponse>

    @POST("/Api/User/Get")
    fun loadAllUsers(@Body body: AllUsersRequest): Single<AllUsersResponse>

    @POST("/Api/Client/Update")
    fun updateProfile(@Body body: EditProfileRequest): Completable

    @POST("/Api/Client/UpdateClientPhoto")
    fun updateUserAvatar(@Query("fileId") avatarFieldId: Int): Completable
}

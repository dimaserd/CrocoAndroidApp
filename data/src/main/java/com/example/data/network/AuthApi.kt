package com.example.data.network

import com.example.data.entity.login.LoginRequest
import com.example.data.entity.login.LoginResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/Api/Account/Login/ByEmail")
    fun login(@Body loginRequest: LoginRequest): Single<LoginResponse>

    @POST("/Api/Account/LogOut")
    fun logout(): Completable
}

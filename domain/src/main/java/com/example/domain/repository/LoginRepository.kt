package com.example.domain.repository

import io.reactivex.Completable
import io.reactivex.Single

interface LoginRepository {

    fun login(email: String, password: String): Single<Boolean>

    fun logout(): Completable

    fun isLoggedIn(): Boolean
}

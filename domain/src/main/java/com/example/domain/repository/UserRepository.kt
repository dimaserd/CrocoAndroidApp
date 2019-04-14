package com.example.domain.repository

import com.example.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun loadUser(): Single<User>

    fun loadAllUsers(): Single<List<User>>

    fun updateProfile(user: User): Completable
}

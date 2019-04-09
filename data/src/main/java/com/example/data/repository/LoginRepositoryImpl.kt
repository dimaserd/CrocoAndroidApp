package com.example.data.repository

import com.example.data.converter.LoginConverter
import com.example.data.network.LoginApi
import com.example.domain.model.User
import com.example.domain.repository.LoginRepository
import io.reactivex.Completable
import io.reactivex.Single

class LoginRepositoryImpl(private val loginApi: LoginApi) : LoginRepository {

    override fun login(email: String, password: String): Single<Boolean> {
        return loginApi.login(LoginConverter.toNetwork(email, password)).map { it.isSucceeded }
    }
}

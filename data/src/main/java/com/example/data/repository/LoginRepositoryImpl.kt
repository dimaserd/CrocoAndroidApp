package com.example.data.repository

import com.example.data.converter.LoginConverter
import com.example.data.network.AuthApi
import com.example.data.store.InMemoryStorage
import com.example.domain.repository.LoginRepository
import io.reactivex.Completable
import io.reactivex.Single

class LoginRepositoryImpl(
    private val authApi: AuthApi,
    private val inMemoryStorage: InMemoryStorage
) : LoginRepository {

    override fun login(email: String, password: String): Single<Boolean> {
        return authApi.login(LoginConverter.toNetwork(email, password)).map { it.isSucceeded }
    }

    override fun logout(): Completable {
        return authApi.logout()
    }

    override fun isLoggedIn(): Boolean {
        return inMemoryStorage.getAuthToken() != null
    }
}

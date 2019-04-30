package com.example.data.repository

import com.example.data.converter.LoginConverter
import com.example.data.network.AuthApi
import com.example.data.store.PersistentStorage
import com.example.domain.repository.LoginRepository
import io.reactivex.Completable
import io.reactivex.Single

class LoginRepositoryImpl(
    private val authApi: AuthApi,
    private val persistentStorage: PersistentStorage
) : LoginRepository {

    companion object {

        private val TIME_REGEX = "".toRegex()
    }

    override fun login(email: String, password: String): Single<Boolean> {
        return authApi.login(LoginConverter.toNetwork(email, password, true)).map {
            if (!it.isSucceeded) {
                it.message == "Вы уже авторизованы в системе"
            } else {
                it.isSucceeded
            }
        }
    }

    override fun logout(): Completable {
        return authApi.logout()
    }

    override fun isLoggedIn(): Boolean {
        val cookies = persistentStorage.getCookies()

        // TODO
//        cookies?.forEach { cookie ->
//            val a = SimpleDateFormat("DD-MMMM-YYYY HH:MM:SS", Locale.ENGLISH).parse(cookie)
//            val b = 0
//                11-May-2019 19:59:54
//        }

        return cookies != null
    }
}

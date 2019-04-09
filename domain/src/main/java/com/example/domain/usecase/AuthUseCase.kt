package com.example.domain.usecase

import com.example.domain.repository.LoginRepository
import io.reactivex.Single

class AuthUseCase(private val loginRepository: LoginRepository) {

    fun login(email: String, password: String): Single<Boolean> {
        return loginRepository.login(email, password)
    }
}

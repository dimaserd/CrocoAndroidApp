package com.example.domain.usecase

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserUseCase(private val userRepository: UserRepository) {

    fun loadUser(): Single<User> {
        return userRepository.loadUser()
    }

    fun updateUser(user: User): Completable {
        return userRepository.updateProfile(user)
    }
}
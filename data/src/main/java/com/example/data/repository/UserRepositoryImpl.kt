package com.example.data.repository

import com.example.data.converter.EditProfileConverter
import com.example.data.converter.UserConverter
import com.example.data.network.UserApi
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {

    override fun loadUser(): Single<User> {
        return userApi.loadUser().map { UserConverter.fromNetwork(it) }
    }

    override fun updateProfile(user: User): Completable {
        return userApi.updateProfile(EditProfileConverter.toNetwork(user))
    }
}

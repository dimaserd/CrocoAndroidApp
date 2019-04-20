package com.example.data.repository

import com.example.data.converter.AllUsersConverter
import com.example.data.converter.AvatarConverter
import com.example.data.converter.EditProfileConverter
import com.example.data.converter.UserConverter
import com.example.data.entity.avatar.AvatarResponse
import com.example.data.network.PhotoApi
import com.example.data.network.UserApi
import com.example.domain.model.Avatar
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val photoApi: PhotoApi
) : UserRepository {

    override fun loadUser(): Single<User> {
        return userApi.loadUser().map { UserConverter.fromNetwork(it) }
    }

    override fun loadAvatar(avatarFieldId: Int): Single<Avatar> {
        return photoApi.downloadAvatar(avatarFieldId).map { AvatarConverter.fromNetwork(AvatarResponse(it)) }
    }

    override fun loadAllUsers(): Single<List<User>> {
        return userApi.loadAllUsers(AllUsersConverter.toNetwork()).map { AllUsersConverter.fromNetwork(it) }
    }

    override fun updateProfile(user: User): Completable {
        return userApi.updateProfile(EditProfileConverter.toNetwork(user))
    }

    override fun uploadAvatar(avatar: Avatar): Completable {
        // TODO
        return photoApi.uploadAvatar(0)
    }
}

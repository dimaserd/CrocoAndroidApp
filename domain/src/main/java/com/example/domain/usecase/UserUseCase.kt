package com.example.domain.usecase

import android.net.Uri
import com.example.domain.model.Avatar
import com.example.domain.model.User
import com.example.domain.model.UserWithAvatar
import com.example.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserUseCase(private val userRepository: UserRepository) {

    fun loadUser(): Single<User> {
        return userRepository.loadUser()
    }

    fun loadAvatar(avatarFieldId: Int): Single<Avatar> {
        return userRepository.loadAvatar(avatarFieldId)
    }

    fun loadUserWithAvatar(): Single<UserWithAvatar> {
        return loadUser().flatMap { user ->
            loadAvatar(user.avatarFieldId).map { avatar ->
                UserWithAvatar(user, avatar)
            }
        }
    }

    fun loadAllUsers(): Single<List<User>> {
        return userRepository.loadAllUsers()
    }

    fun updateUser(user: User): Completable {
        return userRepository.updateProfile(user)
    }

    fun uploadAvatar(avatarUri: Uri): Completable {
        return userRepository.uploadAvatar(avatarUri).flatMapCompletable { avatarFileId ->
            userRepository.updateUserAvatar(avatarFileId)
        }
    }
}

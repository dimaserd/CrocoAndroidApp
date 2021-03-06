package com.example.domain.repository

import android.net.Uri
import com.example.domain.model.Avatar
import com.example.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun loadUser(): Single<User>

    fun loadAvatar(avatarFieldId: Int): Single<Avatar>

    fun loadAllUsers(): Single<List<User>>

    fun updateProfile(user: User): Completable

    fun uploadAvatar(avatarUri: Uri): Single<Int>

    fun updateUserAvatar(avatarFileId: Int): Completable
}

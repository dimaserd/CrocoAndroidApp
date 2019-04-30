package com.example.data.repository

import android.net.Uri
import com.example.data.converter.*
import com.example.data.entity.avatar_download.AvatarDownloadResponse
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
        return photoApi.downloadAvatar(avatarFieldId).map {
            AvatarDownloadConverter.fromNetwork(AvatarDownloadResponse(it))
        }
    }

    override fun loadAllUsers(): Single<List<User>> {
        return userApi.loadAllUsers(AllUsersConverter.toNetwork()).map { AllUsersConverter.fromNetwork(it) }
    }

    override fun updateProfile(user: User): Completable {
        return userApi.updateProfile(EditProfileConverter.toNetwork(user))
    }

    override fun uploadAvatar(avatarUri: Uri): Single<Int> {
//        val request = AvatarUploadConverter.toNetwork(avatarUri)
//        val response2 = photoApi.uploadAvatar(request)
//        response2.enqueue(object : Callback<AvatarUploadResponse> {
//            override fun onFailure(call: Call<AvatarUploadResponse>, t: Throwable) {
//                val a = 0
//            }
//
//            override fun onResponse(call: Call<AvatarUploadResponse>, response: Response<AvatarUploadResponse>) {
//                val b = 0
//            }
//        })
//
//        return Single.just(1)

        return photoApi.uploadAvatar(AvatarUploadConverter.toNetwork(avatarUri))
            .map { AvatarUploadConverter.fromNetwork(it) }
    }

    override fun updateUserAvatar(avatarFileId: Int): Completable {
        return userApi.updateUserAvatar(avatarFileId)
    }
}

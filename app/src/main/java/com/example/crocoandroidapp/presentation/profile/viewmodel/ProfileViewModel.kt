package com.example.crocoandroidapp.presentation.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseViewModel
import com.example.crocoandroidapp.presentation.base.LoadingViewState
import com.example.crocoandroidapp.presentation.base.LoadingViewState.SnackBarCommand
import com.example.crocoandroidapp.utils.CommandsLiveData
import com.example.crocoandroidapp.utils.onNext
import com.example.crocoandroidapp.utils.smartSubscribe
import com.example.domain.model.UserWithAvatar
import com.example.domain.usecase.UserUseCase
import com.example.domain.utils.SchedulersProvider
import com.example.domain.utils.schedulersIoToMain

class ProfileViewModel(
    private val userUseCase: UserUseCase,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    init {

        loadUser()
    }

    val profileLiveData = MutableLiveData<UserWithAvatar>()
    val stateCommand = CommandsLiveData<LoadingViewState>()

    fun uploadAvatar(avatarUri: Uri) {
        userUseCase.uploadAvatar(avatarUri)
            .schedulersIoToMain(schedulersProvider)
            .smartSubscribe(
                onSuccess = { stateCommand.onNext(SnackBarCommand(R.string.successful_photo_upload)) },
                onError = { stateCommand.onNext(SnackBarCommand(it)) }
            )
            .disposeOnViewModelDestroy()
    }

    private fun loadUser() {
        userUseCase.loadUserWithAvatar()
            .schedulersIoToMain(schedulersProvider)
            .smartSubscribe(
                onSuccess = { profileLiveData.onNext(it) },
                onError = { stateCommand.onNext(SnackBarCommand(it)) }
            )
            .disposeOnViewModelDestroy()
    }
}

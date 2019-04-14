package com.example.crocoandroidapp.presentation.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.crocoandroidapp.presentation.base.BaseViewModel
import com.example.crocoandroidapp.presentation.base.LoadingViewState
import com.example.crocoandroidapp.presentation.base.LoadingViewState.SnackBarErrorCommand
import com.example.crocoandroidapp.utils.CommandsLiveData
import com.example.crocoandroidapp.utils.onNext
import com.example.crocoandroidapp.utils.smartSubscribe
import com.example.domain.model.User
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

    val userLiveData = MutableLiveData<User>()
    val stateCommand = CommandsLiveData<LoadingViewState>()

    private fun loadUser() {
        userUseCase.loadUser()
            .schedulersIoToMain(schedulersProvider)
            .smartSubscribe(
                onSuccess = { userLiveData.onNext(it) },
                onError = { stateCommand.onNext(SnackBarErrorCommand(it)) }
            )
            .disposeOnViewModelDestroy()
    }
}
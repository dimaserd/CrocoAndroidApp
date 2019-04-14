package com.example.crocoandroidapp.presentation.choosing_users.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.crocoandroidapp.presentation.base.BaseViewModel
import com.example.crocoandroidapp.presentation.choosing_users.state.ChoosingUserViewState
import com.example.crocoandroidapp.presentation.choosing_users.state.ChoosingUserViewState.*
import com.example.crocoandroidapp.utils.CommandsLiveData
import com.example.crocoandroidapp.utils.onNext
import com.example.crocoandroidapp.utils.smartSubscribe
import com.example.domain.model.User
import com.example.domain.usecase.UserUseCase
import com.example.domain.utils.SchedulersProvider
import com.example.domain.utils.schedulersIoToMain

class ChoosingUsersViewModel(
    private val userUseCase: UserUseCase,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    init {

        loadAllUsers()
    }

    val usersLiveData = MutableLiveData<List<User>>()
    val stateCommand = CommandsLiveData<ChoosingUserViewState>()
    val selectedUsers = mutableListOf<User>()

    fun handleUserClicked(user: User) {
        if (selectedUsers.contains(user)) {
            selectedUsers.remove(user)
        } else {
            selectedUsers.add(user)
        }

        if (selectedUsers.size == 0) {
            stateCommand.onNext(HideButtonTasks)
        } else {
            stateCommand.onNext(ShowButtonTasks)
        }
    }

    private fun loadAllUsers() {
        userUseCase.loadAllUsers()
            .schedulersIoToMain(schedulersProvider)
            .smartSubscribe(
                onSuccess = {
                    stateCommand.onNext(Content)
                    usersLiveData.onNext(it)
                },
                onError = { stateCommand.onNext(SnackBarErrorCommand(it)) }
            )
            .disposeOnViewModelDestroy()
    }
}

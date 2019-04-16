package com.example.crocoandroidapp.presentation.tasks.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.crocoandroidapp.presentation.base.BaseViewModel
import com.example.crocoandroidapp.presentation.base.LoadingViewState
import com.example.crocoandroidapp.presentation.base.LoadingViewState.*
import com.example.crocoandroidapp.presentation.tasks.state.MonthViewState
import com.example.crocoandroidapp.utils.CommandsLiveData
import com.example.crocoandroidapp.utils.onNext
import com.example.crocoandroidapp.utils.smartSubscribe
import com.example.domain.model.User
import com.example.domain.usecase.TaskUseCase
import com.example.domain.utils.SchedulersProvider
import com.example.domain.utils.schedulersIoToMain

class MonthsViewModel(
    private val taskUseCase: TaskUseCase,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val monthsLiveData = MutableLiveData<MonthViewState>()
    val stateCommand = CommandsLiveData<LoadingViewState>()

    val users = mutableListOf<User>()

    fun addUsers(newUsers: List<User>) {
        users.addAll(newUsers)
    }

    fun loadTasks(users: List<User>) {
        stateCommand.onNext(Loading)

        taskUseCase.loadTasks(users.map { it.id })
            .schedulersIoToMain(schedulersProvider)
            .smartSubscribe(
                onSuccess = {
                    monthsLiveData.onNext(MonthViewState.MonthWithTasks(tasks = it))
                },
                onError = { stateCommand.onNext(SnackBarErrorCommand(it)) }
            )
            .disposeOnViewModelDestroy()

        stateCommand.onNext(Content)
    }

    fun onCurrentMonthChanged(month: Int) {

    }
}

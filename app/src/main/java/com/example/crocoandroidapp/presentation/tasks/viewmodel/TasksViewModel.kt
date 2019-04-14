package com.example.crocoandroidapp.presentation.tasks.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.crocoandroidapp.presentation.base.BaseViewModel
import com.example.crocoandroidapp.presentation.base.LoadingViewState
import com.example.crocoandroidapp.presentation.base.LoadingViewState.*
import com.example.crocoandroidapp.utils.CommandsLiveData
import com.example.crocoandroidapp.utils.onNext
import com.example.crocoandroidapp.utils.smartSubscribe
import com.example.domain.model.Task
import com.example.domain.model.User
import com.example.domain.usecase.TaskUseCase
import com.example.domain.utils.SchedulersProvider
import com.example.domain.utils.schedulersIoToMain

class TasksViewModel(
    private val taskUseCase: TaskUseCase,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val tasksLiveData = MutableLiveData<List<Task>>()
    val stateCommand = CommandsLiveData<LoadingViewState>()
    val users = mutableListOf<User>()
    val showedUser = User("", "", null, null, null, null, null, null)
    val tasks = mapOf<User, Task>()

    fun addUsers(newUsers: List<User>) {
        users.addAll(newUsers)
    }

    fun loadTasks(users: List<User>) {
        stateCommand.onNext(Loading)

        taskUseCase.loadTasks(users.map { it.id })
            .schedulersIoToMain(schedulersProvider)
            .smartSubscribe(
                onSuccess = { tasksLiveData.onNext(it) },
                onError = { stateCommand.onNext(SnackBarErrorCommand(it)) }
            )
            .disposeOnViewModelDestroy()

        stateCommand.onNext(Content)
    }
}

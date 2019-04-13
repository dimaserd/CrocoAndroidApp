package com.example.crocoandroidapp.presentation.tasks.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.crocoandroidapp.presentation.base.BaseViewModel
import com.example.crocoandroidapp.presentation.base.LoadingViewState
import com.example.crocoandroidapp.presentation.base.LoadingViewState.*
import com.example.crocoandroidapp.utils.CommandsLiveData
import com.example.crocoandroidapp.utils.onNext
import com.example.crocoandroidapp.utils.smartSubscribe
import com.example.domain.model.Task
import com.example.domain.usecase.TaskUseCase
import com.example.domain.utils.SchedulersProvider
import com.example.domain.utils.schedulersIoToMain

class TasksViewModel(
    private val taskUseCase: TaskUseCase,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val tasksLiveData = MutableLiveData<List<Task>>()
    val stateCommand = CommandsLiveData<LoadingViewState>()

    fun loadTasks(userIds: List<String>, taskId: String) {
        stateCommand.onNext(Loading)

        taskUseCase.loadTasks(userIds, taskId)
            .schedulersIoToMain(schedulersProvider)
            .smartSubscribe(
                onSuccess = { tasksLiveData.onNext(it) },
                onError = { stateCommand.onNext(SnackBarErrorCommand(it)) }
            )
            .disposeOnViewModelDestroy()

        stateCommand.onNext(Content)
    }
}

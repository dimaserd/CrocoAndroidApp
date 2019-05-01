package com.example.crocoandroidapp.presentation.tasks.state

import com.example.domain.model.Task

sealed class TasksViewState {

    data class MonthsWithTasks(
        val monthWithTasks: List<MonthWithTasks>
    ) : TasksViewState()

}

data class MonthWithTasks(
    val monthName: String,
    val tasks: List<Task> = listOf()
)

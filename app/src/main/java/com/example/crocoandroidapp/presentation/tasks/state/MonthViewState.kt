package com.example.crocoandroidapp.presentation.tasks.state

import com.example.domain.model.Task

sealed class MonthViewState {

    data class MonthWithTasks(
        val month: String = "",
        val tasks: List<Task> = listOf(),
        val currentPosition: Int = 0
    ) : MonthViewState()
}

package com.example.domain.usecase

import com.example.domain.model.Task
import com.example.domain.repository.TaskRepository
import io.reactivex.Single

class TaskUseCase(private val taskRepository: TaskRepository) {

    fun loadTasks(userIds: List<String>): Single<List<Task>> {
        return taskRepository.loadTasks(userIds)
    }
}
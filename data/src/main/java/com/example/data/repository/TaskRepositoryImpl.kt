package com.example.data.repository

import com.example.data.converter.TaskConverter
import com.example.data.network.TaskApi
import com.example.domain.model.Task
import com.example.domain.repository.TaskRepository
import io.reactivex.Single

class TaskRepositoryImpl(private val taskApi: TaskApi) : TaskRepository {

    override fun loadTasks(userIds: List<String>, taskId: String): Single<List<Task>> {
        return taskApi.getTasks(TaskConverter.toNetwork(12, userIds, taskId)).map { TaskConverter.fromNetwork(it) }
    }
}
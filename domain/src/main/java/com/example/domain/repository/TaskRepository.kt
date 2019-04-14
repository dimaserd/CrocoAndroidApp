package com.example.domain.repository

import com.example.domain.model.Task
import io.reactivex.Single

interface TaskRepository {

    fun loadTasks(userIds: List<String>): Single<List<Task>>
}
package com.example.data.network

import com.example.data.entity.task.TaskRequest
import com.example.data.entity.task.TaskResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskApi {

    @POST("/Api/DayTask/GetTasks")
    fun getTasks(
        @Body body: TaskRequest
    ): Single<List<TaskResponse>>
}
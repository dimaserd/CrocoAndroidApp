package com.example.data.entity.task

import com.google.gson.annotations.SerializedName

class TaskRequest(

    @SerializedName("MonthShift")
    val monthShift: Int,

    @SerializedName("UserIds")
    val userIds: List<String>,

    @SerializedName("ShowTasksWithNoAssignee")
    val showTasksWithNoAssignee: Boolean
)

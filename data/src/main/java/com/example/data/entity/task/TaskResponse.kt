package com.example.data.entity.task

import com.google.gson.annotations.SerializedName
import java.util.*

class TaskResponse(

    @SerializedName("Id")
    val id: String,

    @SerializedName("TaskDate")
    val date: Date,

    @SerializedName("TaskText")
    val text: String,

    @SerializedName("TaskTitle")
    val title: String,

    @SerializedName("FinishDate")
    val finishDate: Date,

    @SerializedName("TaskTarget")
    val target: String,

    @SerializedName("TaskReview")
    val review: String,

    @SerializedName("TaskComment")
    val comment: String,

    @SerializedName("Comments")
    val comments: List<TaskResponseComment>,

    @SerializedName("Author")
    val author: TaskResponseAuthor,

    @SerializedName("AssigneeUser")
    val user: TaskResponseAuthor
)
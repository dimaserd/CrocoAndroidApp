package com.example.data.entity.task

import com.google.gson.annotations.SerializedName

class TaskResponseComment(

    @SerializedName("Id")
    val id: String,

    @SerializedName("Comment")
    val comment: String,

    @SerializedName("Author")
    val author: TaskResponseAuthor
)
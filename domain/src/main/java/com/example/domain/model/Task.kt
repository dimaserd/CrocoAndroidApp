package com.example.domain.model

import java.util.*

data class Task(
    val id: String,
    val date: Date,
    val text: String,
    val title: String,
    val finishDate: Date,
    val target: String,
    val review: String,
    val comment: String,
    val comments: List<Comment>,
    val author: Author,
    val user: User
)

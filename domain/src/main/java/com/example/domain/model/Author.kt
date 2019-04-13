package com.example.domain.model

data class Author(
    val id: String,
    val name: String,
    val secondName: String,
    val thirdName: String?,
    val email: String?,
    val avatarFileId: Int
)

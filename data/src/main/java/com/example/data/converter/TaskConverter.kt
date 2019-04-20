package com.example.data.converter

import com.example.data.entity.task.TaskRequest
import com.example.data.entity.task.TaskResponse
import com.example.data.entity.task.TaskResponseAuthor
import com.example.data.entity.task.TaskResponseComment
import com.example.domain.model.Author
import com.example.domain.model.Comment
import com.example.domain.model.Task
import com.example.domain.model.User
import com.example.domain.utils.exceptions.ConvertException

object TaskConverter {

    fun toNetwork(monthShift: Int, userIds: List<String>): TaskRequest {
        return TaskRequest(
            monthShift = monthShift,
            userIds = userIds,
            showTasksWithNoAssignee = true
        )
    }

    fun fromNetwork(task: List<TaskResponse>?): List<Task> {
        return task?.map {
            Task(
                id = it.id,
                date = it.date,
                text = it.text,
                title = it.title,
                finishDate = it.finishDate,
                target = it.target,
                review = it.review,
                comment = it.comment,
                comments = it.comments.map(::convertComment),
                author = convertAuthor(it.author),
                user = convertUser(it.user)
            )
        } ?: throw ConvertException("task is null")
    }

    private fun convertComment(comment: TaskResponseComment): Comment {
        return Comment(
            id = comment.id,
            comment = comment.comment,
            author = convertAuthor(comment.author)
        )
    }

    private fun convertAuthor(author: TaskResponseAuthor): Author {
        return Author(
            id = author.id,
            name = author.name,
            secondName = author.surname,
            thirdName = author.patronymic,
            email = author.email,
            avatarFileId = author.avatarFileId
        )
    }

    private fun convertUser(author: TaskResponseAuthor): User {
        return User(
            firstName = author.name,
            secondName = author.surname,
            thirdName = author.patronymic,
            email = author.email,
            avatarFieldId = author.avatarFileId,
            birthDate = null,
            phoneNumber = null,
            sex = null
        )
    }
}

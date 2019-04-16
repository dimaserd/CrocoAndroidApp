package com.example.crocoandroidapp.presentation.tasks.adapter.item

import com.example.crocoandroidapp.R
import com.example.domain.model.Task
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_tasks_view_pager_task_item.fragment_tasks_view_pager_tasks_text_view_name as textViewName

data class TaskItem(private val task: Task) : Item() {

    override fun getLayout(): Int = R.layout.fragment_tasks_view_pager_task_item

    override fun getId(): Long = layout.toLong()

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            textViewName.text = task.title
        }
    }
}
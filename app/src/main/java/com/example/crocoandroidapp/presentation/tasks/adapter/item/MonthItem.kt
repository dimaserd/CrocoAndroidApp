package com.example.crocoandroidapp.presentation.tasks.adapter.item

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Task
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_tasks.view.fragment_tasks_floating_action_bar_edit as buttonAdd
import kotlinx.android.synthetic.main.fragment_tasks.view.fragment_tasks_recycler_view_tasks as recyclerViewTasks
import kotlinx.android.synthetic.main.fragment_tasks_view_pager_task_item.view.fragment_tasks_view_pager_tasks_text_view_name as textViewName

class MonthItem(
    private val tasks: List<Task>,
    private val onAddButtonClicked: (MonthItem) -> Unit
) {

    fun bind(layout: View) {
        val taskAdapter = GroupAdapter<ViewHolder>()
        val taskLayoutManager = LinearLayoutManager(layout.context)
        taskAdapter.update(tasks.map(::TaskItem))

        with(layout) {
            recyclerViewTasks.apply {
                adapter = taskAdapter
                layoutManager = taskLayoutManager
            }

            buttonAdd.setOnClickListener { onAddButtonClicked.invoke(this@MonthItem) }
        }
    }
}

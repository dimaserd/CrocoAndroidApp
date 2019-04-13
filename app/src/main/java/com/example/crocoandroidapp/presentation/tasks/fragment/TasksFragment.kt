package com.example.crocoandroidapp.presentation.tasks.fragment

import android.os.Bundle
import android.view.View
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseFragment
import com.example.crocoandroidapp.presentation.base.LoadingViewState
import com.example.crocoandroidapp.presentation.base.LoadingViewState.*
import com.example.crocoandroidapp.presentation.tasks.viewmodel.TasksViewModel
import com.example.crocoandroidapp.utils.makeGone
import com.example.crocoandroidapp.utils.makeVisible
import com.example.crocoandroidapp.utils.observe
import com.example.domain.model.Task
import org.koin.android.ext.android.inject
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_constraint_layout_content as content
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_progress_bar_loading as progressBar
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_recycler_view_tasks as tasks
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_text_view_zero_screen as zeroScreen

class TasksFragment : BaseFragment() {

    private val viewModel by inject<TasksViewModel>()

    override fun getLayout() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateCommand, ::onStateChanged)
        observe(viewModel.tasksLiveData, ::onTasksChanged)

        arguments?.let {
            // TODO
//            viewModel.loadTasks(it.getStringArrayList(""), it.getString(""))
        }

        // TODO remove
        viewModel.loadTasks(listOf("1", "2"), "3")

        showLoading()
    }

    private fun onStateChanged(state: LoadingViewState) {
        when (state) {
            is Content -> showContent()
            is Loading -> showLoading()
            is NoData -> showZeroScreen()
            is SnackBarErrorCommand -> {
                showZeroScreen()
                showSnackbar(state.messageResource)
            }
        }
    }

    private fun onTasksChanged(tasks: List<Task>) {
        showContent()
    }

    private fun showContent() {
        content.makeVisible()
        progressBar.makeGone()
        zeroScreen.makeGone()
    }

    private fun showLoading() {
        content.makeGone()
        progressBar.makeVisible()
        zeroScreen.makeGone()
    }

    private fun showZeroScreen() {
        content.makeGone()
        progressBar.makeGone()
        zeroScreen.makeVisible()
    }
}

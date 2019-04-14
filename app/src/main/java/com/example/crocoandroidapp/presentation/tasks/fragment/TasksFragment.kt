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
import com.example.domain.model.UserList
import org.koin.android.ext.android.inject
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_constraint_layout_content as content
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_floating_action_bar_edit as buttonAdd
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_image_button_back as buttonBack
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_image_button_forward as buttonForward
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_progress_bar_loading as progressBar
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_recycler_view_tasks as tasks
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_spinner_users as spinnerUsers
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_text_view_current_month as textViewCurrentMonth
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_text_view_zero_screen as zeroScreen

class TasksFragment : BaseFragment() {

    companion object {

        const val USERS_EXTRA = "USERS_EXTRA"
    }

    private val viewModel by inject<TasksViewModel>()

    override fun getLayout() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateCommand, ::onStateChanged)
        observe(viewModel.tasksLiveData, ::onTasksChanged)

        arguments?.let {
            it.getParcelable<UserList>(USERS_EXTRA)?.let { userList ->
                viewModel.addUsers(userList.users)
                viewModel.loadTasks(userList.users)
            }
        }

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

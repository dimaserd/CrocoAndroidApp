package com.example.crocoandroidapp.presentation.choosing_users.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseFragment
import com.example.crocoandroidapp.presentation.choosing_users.state.ChoosingUserViewState
import com.example.crocoandroidapp.presentation.choosing_users.state.ChoosingUserViewState.*
import com.example.crocoandroidapp.presentation.choosing_users.item.UserItem
import com.example.crocoandroidapp.presentation.choosing_users.viewmodel.ChoosingUsersViewModel
import com.example.crocoandroidapp.presentation.tasks.fragment.TasksFragment.Companion.USERS_EXTRA
import com.example.crocoandroidapp.utils.makeGone
import com.example.crocoandroidapp.utils.makeVisible
import com.example.crocoandroidapp.utils.observe
import com.example.domain.model.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import org.koin.android.ext.android.inject
import kotlinx.android.synthetic.main.fragment_choosing_users.fragment_choosing_users_recycler_view_users as recyclerViewUsers
import kotlinx.android.synthetic.main.fragment_choosing_users.fragment_choosing_users_text_view_zero_screen as zeroScreen
import kotlinx.android.synthetic.main.fragment_choosing_users.fragment_choosing_users_progress_bar_loading as progressBar
import kotlinx.android.synthetic.main.fragment_choosing_users.fragment_profile_constraint_layout_content as content
import kotlinx.android.synthetic.main.fragment_choosing_users.fragment_choosing_users_floating_action_bar_tasks as buttonTasks

class ChoosingUsersFragment : BaseFragment() {

    private val viewModel by inject<ChoosingUsersViewModel>()
    private val usersAdapter = GroupAdapter<ViewHolder>()

    override fun getLayout() = R.layout.fragment_choosing_users

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateCommand, ::onStateChanged)
        observe(viewModel.usersLiveData, ::onUsersChanged)

        initViews()
        showLoading()
    }

    private fun initViews() {
        recyclerViewUsers.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(context)
        }

        buttonTasks.setOnClickListener {
            navigateToTasks()
        }
    }

    private fun onStateChanged(state: ChoosingUserViewState) {
        when (state) {
            is Content -> showContent()
            is Loading -> showLoading()
            is NoData -> showZeroScreen()
            is ShowButtonTasks -> buttonTasks.show()
            is HideButtonTasks -> buttonTasks.hide()
            is SnackBarErrorCommand -> {
                showZeroScreen()
                showSnackbar(state.messageResource)
            }
        }
    }

    private fun onUsersChanged(users: List<User>) {
        usersAdapter.update(users.map { UserItem(it, viewModel::handleUserClicked) })
    }

    private fun navigateToTasks() {
        val bundle = bundleOf(
            USERS_EXTRA to viewModel.selectedUsers
        )
        findNavController().navigate(R.id.action_choosing_users_to_fragment_tasks, bundle)
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

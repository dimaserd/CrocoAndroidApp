package com.example.crocoandroidapp.presentation.tasks.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.add_task.fragment.AddTaskFragment.Companion.MONTH_EXTRA
import com.example.crocoandroidapp.presentation.base.BaseFragment
import com.example.crocoandroidapp.presentation.base.LoadingViewState
import com.example.crocoandroidapp.presentation.base.LoadingViewState.*
import com.example.crocoandroidapp.presentation.tasks.adapter.MonthViewPagerAdapter
import com.example.crocoandroidapp.presentation.tasks.adapter.item.MonthItem
import com.example.crocoandroidapp.presentation.tasks.state.MonthViewState
import com.example.crocoandroidapp.presentation.tasks.viewmodel.MonthsViewModel
import com.example.crocoandroidapp.utils.makeGone
import com.example.crocoandroidapp.utils.makeVisible
import com.example.crocoandroidapp.utils.observe
import com.example.crocoandroidapp.utils.setOnPageChangedListener
import com.example.domain.model.User
import com.example.domain.model.UserList
import org.koin.android.ext.android.inject
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_constraint_layout_content as content
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_image_button_back as buttonBack
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_image_button_forward as buttonForward
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_progress_bar_loading as progressBar
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_recycler_view_tasks as tasks
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_spinner_users as spinnerUsers
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_text_view_current_month as textViewCurrentMonth
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_text_view_zero_screen as zeroScreen
import kotlinx.android.synthetic.main.fragment_tasks.fragment_tasks_view_pager_months as viewPagerMonths

class TasksFragment : BaseFragment() {

    companion object {

        const val USERS_EXTRA = "USERS_EXTRA"
    }

    private val viewModel by inject<MonthsViewModel>()
    private lateinit var monthsAdapter: MonthViewPagerAdapter

    override fun getLayout() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateCommand, ::onStateChanged)
        observe(viewModel.monthsLiveData, ::onTasksChanged)

        arguments?.let {
            it.getParcelable<UserList>(USERS_EXTRA)?.let { userList ->
                viewModel.addUsers(userList.users)
                viewModel.loadTasks(userList.users)
            }
        }

        showLoading()
        initViews()
    }

    private fun initViews() {
        val usersAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            addAll(viewModel.users.map { it.firstName })
        }

        spinnerUsers.apply {
            adapter = usersAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    onShowedUserChanged(viewModel.users[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        buttonBack.setOnClickListener { viewPagerMonths.currentItem -= 1 }
        buttonForward.setOnClickListener { viewPagerMonths.currentItem += 1 }

        monthsAdapter = MonthViewPagerAdapter(requireContext())
        viewPagerMonths.adapter = monthsAdapter

        viewPagerMonths.setOnPageChangedListener {
            viewModel.onCurrentMonthChanged(it)
        }
    }

    private fun onStateChanged(state: LoadingViewState) {
        when (state) {
            is Content -> showContent()
            is Loading -> showLoading()
            is NoData -> showZeroScreen()
            is SnackBarCommand -> {
                showZeroScreen()
                showSnackbar(state.messageResource)
            }
        }
    }

    private fun onTasksChanged(months: MonthViewState) {
        showContent()
        when (months) {
            is MonthViewState.MonthWithTasks -> {
                val onAddButtonClicked = { month: MonthItem ->
                    val bundle = bundleOf(MONTH_EXTRA to month)
                    findNavController().navigate(R.id.action_fragment_tasks_to_add_task, bundle)
                }

//                monthsAdapter.addMonths()
            }
        }
    }

    private fun onShowedUserChanged(user: User) {
        // TODO
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

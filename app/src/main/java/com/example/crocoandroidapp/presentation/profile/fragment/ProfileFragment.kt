package com.example.crocoandroidapp.presentation.profile.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseFragment
import com.example.crocoandroidapp.presentation.base.LoadingViewState
import com.example.crocoandroidapp.presentation.base.LoadingViewState.*
import com.example.crocoandroidapp.presentation.edit_profile.fragment.EditProfileFragment.Companion.USER_EXTRA
import com.example.crocoandroidapp.presentation.profile.viewmodel.ProfileViewModel
import com.example.crocoandroidapp.utils.makeGone
import com.example.crocoandroidapp.utils.makeVisible
import com.example.crocoandroidapp.utils.observe
import com.example.domain.model.User
import com.example.domain.utils.SexConverter
import org.koin.android.ext.android.inject
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_constraint_layout_content as content
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_floating_action_bar_edit as editFab
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_progress_bar_loading as progressBar
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_email as textViewEmail
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_name as textViewName
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_phone_number as textViewPhoneNumber
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_second_name as textViewSecondName
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_sex as textViewSex
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_third_name as textViewThirdName
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_zero_screen as zeroScreen

class ProfileFragment : BaseFragment() {

    private val viewModel by inject<ProfileViewModel>()

    override fun getLayout() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateCommand, ::onStateChanged)
        observe(viewModel.userLiveData, ::onUserChanged)

        initViews()
        showLoading()
    }

    private fun initViews() {
        editFab.setOnClickListener {
            val bundle = bundleOf(
                USER_EXTRA to viewModel.userLiveData.value
            )
            findNavController().navigate(R.id.action_profile_to_edit_profile, bundle)
        }
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

    private fun onUserChanged(user: User) {
        showContent()
        with(user) {
            textViewName.text = firstName
            textViewSecondName.text = secondName ?: getString(R.string.not_mentioned)
            textViewThirdName.text = thirdName ?: getString(R.string.not_mentioned)
            textViewSex.text = getString(SexConverter.convertToResId(sex))
            textViewEmail.text = email
            textViewPhoneNumber.text = phoneNumber
        }
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

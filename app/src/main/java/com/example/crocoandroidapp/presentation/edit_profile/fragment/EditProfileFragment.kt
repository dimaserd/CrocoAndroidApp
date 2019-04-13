package com.example.crocoandroidapp.presentation.edit_profile.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseFragment
import com.example.crocoandroidapp.presentation.edit_profile.state.EditProfileViewState
import com.example.crocoandroidapp.presentation.edit_profile.viewmodel.EditProfileViewModel
import com.example.crocoandroidapp.utils.makeGone
import com.example.crocoandroidapp.utils.makeVisible
import com.example.crocoandroidapp.utils.observe
import com.example.crocoandroidapp.utils.setOnTextChangedListener
import com.example.domain.model.Sex
import com.example.domain.model.User
import org.koin.android.ext.android.inject
import java.util.*
import kotlinx.android.synthetic.main.fragment_edit_profile.fragment_edit_profile_constraint_layout_content as content
import kotlinx.android.synthetic.main.fragment_edit_profile.fragment_edit_profile_date_picker_birth_date as datePickerBirthDate
import kotlinx.android.synthetic.main.fragment_edit_profile.fragment_edit_profile_material_button_save as buttonSafe
import kotlinx.android.synthetic.main.fragment_edit_profile.fragment_edit_profile_progress_bar_loading as progressBar
import kotlinx.android.synthetic.main.fragment_edit_profile.fragment_edit_profile_radio_group_sex as radioGroupSex
import kotlinx.android.synthetic.main.fragment_edit_profile.fragment_edit_profile_text_input_edit_text_name as editTextName
import kotlinx.android.synthetic.main.fragment_edit_profile.fragment_edit_profile_text_input_edit_text_phone_number as editTextPhoneNumber
import kotlinx.android.synthetic.main.fragment_edit_profile.fragment_edit_profile_text_input_edit_text_second_name as editTextSecondName
import kotlinx.android.synthetic.main.fragment_edit_profile.fragment_edit_profile_text_input_edit_text_third_name as editTextThirdName
import kotlinx.android.synthetic.main.fragment_edit_profile.fragment_edit_profile_text_input_layout_name as editTextLayoutName
import kotlinx.android.synthetic.main.fragment_edit_profile.fragment_edit_profile_text_input_layout_phone_number as editTextLayoutPhoneNumber

class EditProfileFragment : BaseFragment() {

    companion object {

        const val USER_EXTRA = "USER_EXTRA"
    }

    private val viewModel by inject<EditProfileViewModel>()

    override fun getLayout() = R.layout.fragment_edit_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            getParcelable<User>(USER_EXTRA)?.let { viewModel.setUser(it) }
        }

        observe(viewModel.stateCommand, ::onStateChanged)
        observe(viewModel.userLiveData, ::onUserChanged)

        initViews()
    }

    private fun initViews() {
        buttonSafe.setOnClickListener {
            viewModel.updateProfile()
        }

        editTextName.setOnTextChangedListener {
            if (!viewModel.checkName(it)) {
                editTextLayoutName.error = getString(R.string.wrong_name)
            } else {
                editTextLayoutName.error = null
            }

            val newUser = viewModel.userLiveData.value!!.copy(firstName = it)
            viewModel.setUser(newUser)
        }

        editTextPhoneNumber.setOnTextChangedListener {
            if (!viewModel.checkPhoneNumber(it)) {
                editTextLayoutPhoneNumber.error = getString(R.string.wrong_phone_number)
            } else {
                editTextLayoutPhoneNumber.error = null
            }

            val newUser = viewModel.userLiveData.value!!.copy(phoneNumber = it)
            viewModel.setUser(newUser)
        }

        editTextSecondName.setOnTextChangedListener {
            val newUser = viewModel.userLiveData.value!!.copy(secondName = it)
            viewModel.setUser(newUser)
        }

        editTextThirdName.setOnTextChangedListener {
            val newUser = viewModel.userLiveData.value!!.copy(thirdName = it)
            viewModel.setUser(newUser)
        }

        radioGroupSex.setOnCheckedChangeListener { group, checkedId ->
            val newUser = viewModel.userLiveData.value!!.copy(sex = convertIdToSex(checkedId))
            viewModel.setUser(newUser)
        }

        showContent()
    }

    private fun onStateChanged(state: EditProfileViewState) {
        when (state) {
            is EditProfileViewState.Success -> findNavController().navigate(R.id.action_edit_profile_to_profile)
            is EditProfileViewState.Updating -> showLoading()
            is EditProfileViewState.SnackBarErrorCommand -> {
                showContent()
                showSnackbar(state.messageResource)
            }
        }
    }

    private fun onUserChanged(user: User) {
        with(user) {
            editTextName.setText(firstName)
            editTextPhoneNumber.setText(phoneNumber)
            editTextSecondName.setText(secondName)
            editTextThirdName.setText(thirdName)

            birthDate?.let {
                val calendar = Calendar.getInstance().apply { time = it }
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                datePickerBirthDate.init(year, month, day) { _, initYear, initMonth, initDay ->
                    val newUser = viewModel.userLiveData.value!!.copy(birthDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, initYear)
                        set(Calendar.MONTH, initMonth)
                        set(Calendar.DAY_OF_MONTH, initDay)
                    }.time)
                    viewModel.setUser(newUser)
                }
            }

            val sexId = convertSexToId(sex)
            radioGroupSex.check(sexId)
        }
    }

    private fun showLoading() {
        progressBar.makeVisible()
    }

    private fun showContent() {
        progressBar.makeGone()
    }

    private fun convertSexToId(sex: Sex?) = when (sex) {
        Sex.MALE -> R.id.fragment_edit_profile_radio_button_male
        Sex.FEMALE -> R.id.fragment_edit_profile_radio_button_female
        else -> R.id.fragment_edit_profile_radio_button_undefined
    }

    private fun convertIdToSex(id: Int) = when (id) {
        R.id.fragment_edit_profile_radio_button_male -> Sex.MALE
        R.id.fragment_edit_profile_radio_button_female -> Sex.FEMALE
        else -> Sex.UNDEFINED
    }
}

package com.example.crocoandroidapp.presentation.edit_profile.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseViewModel
import com.example.crocoandroidapp.presentation.edit_profile.state.EditProfileViewState
import com.example.crocoandroidapp.presentation.edit_profile.state.EditProfileViewState.*
import com.example.crocoandroidapp.utils.CommandsLiveData
import com.example.crocoandroidapp.utils.onNext
import com.example.crocoandroidapp.utils.smartSubscribe
import com.example.domain.model.User
import com.example.domain.usecase.UserUseCase
import com.example.domain.utils.SchedulersProvider
import com.example.domain.utils.schedulersIoToMain

class EditProfileViewModel(
    private val userUseCase: UserUseCase,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val userLiveData = MutableLiveData<User>()
    val stateCommand = CommandsLiveData<EditProfileViewState>()

    fun setUser(user: User) {
        if (user != userLiveData.value) {
            userLiveData.onNext(user)
        }
    }

    fun updateProfile() {
        if (checkFields()) {
            stateCommand.onNext(Updating)
            userLiveData.value?.let { user ->
                userUseCase.updateUser(user)
                    .schedulersIoToMain(schedulersProvider)
                    .smartSubscribe(
                        onSuccess = { stateCommand.onNext(Success) },
                        onError = { stateCommand.onNext(SnackBarErrorCommand(it)) }
                    )
                    .disposeOnViewModelDestroy()
            }
        } else {
            stateCommand.onNext(SnackBarErrorCommand(R.string.wrong_field))
        }
    }

    private fun checkFields() =
        checkName(userLiveData.value?.firstName) && checkPhoneNumber(userLiveData.value?.phoneNumber)

    fun checkName(name: String?) = name?.isNotBlank() ?: false

    fun checkPhoneNumber(phone: String?) = phone?.isNotBlank() ?: false
}
package com.example.crocoandroidapp.presentation.edit_profile.viewmodel

import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseViewModel
import com.example.crocoandroidapp.presentation.edit_profile.state.EditProfileViewState
import com.example.crocoandroidapp.presentation.edit_profile.state.EditProfileViewState.*
import com.example.crocoandroidapp.utils.CommandsLiveData
import com.example.crocoandroidapp.utils.smartSubscribe
import com.example.domain.model.User
import com.example.domain.usecase.UserUseCase
import com.example.domain.utils.SchedulersProvider
import com.example.domain.utils.schedulersIoToMain

class EditProfileViewModel(
    private val userUseCase: UserUseCase,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    var user = User("", "", null, null, null, null, null, null, 0)
    val stateCommand = CommandsLiveData<EditProfileViewState>()

    fun copyUser(newUser: User) {
        user = user.copy(
            firstName = newUser.firstName,
            secondName = newUser.secondName,
            thirdName = newUser.thirdName,
            phoneNumber = newUser.phoneNumber,
            birthDate = newUser.birthDate,
            sex = newUser.sex
        )
    }

    fun updateProfile() {
        if (checkFields()) {
            stateCommand.onNext(Updating)
            userUseCase.updateUser(user)
                .schedulersIoToMain(schedulersProvider)
                .smartSubscribe(
                    onSuccess = { stateCommand.onNext(Success) },
                    onError = { stateCommand.onNext(SnackBarErrorCommand(it)) }
                )
                .disposeOnViewModelDestroy()
        } else {
            stateCommand.onNext(SnackBarErrorCommand(R.string.wrong_field))
        }
    }

    private fun checkFields(): Boolean = checkName(user.firstName) && checkPhoneNumber(user.phoneNumber)

    fun checkName(name: String?) = name?.isNotBlank() ?: false

    fun checkPhoneNumber(phone: String?) = phone?.isNotBlank() ?: false
}
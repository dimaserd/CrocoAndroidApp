package com.example.crocoandroidapp.presentation.login.viewmodel

import android.util.Patterns
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseViewModel
import com.example.crocoandroidapp.presentation.login.state.LoginViewState
import com.example.crocoandroidapp.utils.CommandsLiveData
import com.example.crocoandroidapp.utils.smartSubscribe
import com.example.domain.usecase.AuthUseCase
import com.example.domain.utils.SchedulersProvider
import com.example.domain.utils.schedulersIoToMain

class LoginViewModel(
    private val authUseCase: AuthUseCase,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    companion object {

        private const val TAG = "LoginViewModel"
        private val emailPattern = Patterns.EMAIL_ADDRESS
    }

    private var email = ""
    private var password = ""

    val stateCommand = CommandsLiveData<LoginViewState>()

    init {

        if (authUseCase.isLoggedIn()) {
            navigateToMain()
        }
    }

    fun onEmailEntered(email: String) {
        this.email = email

        if (checkEmailFormat(email)) {
            stateCommand.onNext(LoginViewState.RightEmail)
        } else {
            stateCommand.onNext(LoginViewState.WrongEmail)
        }
    }

    fun onPasswordEntered(password: String) {
        this.password = password
    }

    fun onLoginButtonClicked() {
        if (isCredentialsOk()) {
            stateCommand.onNext(LoginViewState.Loading)

            authUseCase.login(email, password)
                .schedulersIoToMain(schedulersProvider)
                .smartSubscribe(
                    onSuccess = { handleSuccess(it) },
                    onError = { stateCommand.onNext(LoginViewState.SnackBarErrorCommand(it)) }
                )
                .disposeOnViewModelDestroy()
        } else {
            stateCommand.onNext(LoginViewState.SnackBarErrorCommand(R.string.wrong_credentials_format))
        }
    }

    private fun handleSuccess(isSucceeded: Boolean) {
        if (isSucceeded) {
            navigateToMain()
        } else {
            stateCommand.onNext(LoginViewState.SnackBarErrorCommand(R.string.failed_login))
        }
    }

    private fun navigateToMain() {
        stateCommand.onNext(LoginViewState.Success)
    }

    private fun isCredentialsOk() = checkEmailFormat(email) and checkPasswordLength(password)

    private fun checkEmailFormat(email: String): Boolean = emailPattern.matcher(email).matches()

    private fun checkPasswordLength(password: String): Boolean = password.isNotEmpty()
}

package com.example.crocoandroidapp.presentation.login.state

sealed class LoginViewState {

    object Content : LoginViewState()

    object Loading : LoginViewState()

    object Success : LoginViewState()

    object RightEmail : LoginViewState()

    object WrongEmail : LoginViewState()

    class SnackBarErrorCommand(val messageResource: Int) : LoginViewState()
}

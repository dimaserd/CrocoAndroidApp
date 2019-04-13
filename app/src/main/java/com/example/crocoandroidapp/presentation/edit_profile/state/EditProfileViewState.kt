package com.example.crocoandroidapp.presentation.edit_profile.state

sealed class EditProfileViewState {

    object Success : EditProfileViewState()

    object Updating : EditProfileViewState()

    class SnackBarErrorCommand(val messageResource: Int) : EditProfileViewState()
}
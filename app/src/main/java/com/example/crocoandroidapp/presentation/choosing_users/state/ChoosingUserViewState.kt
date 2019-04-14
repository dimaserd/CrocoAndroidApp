package com.example.crocoandroidapp.presentation.choosing_users.state

sealed class ChoosingUserViewState {

    object Content : ChoosingUserViewState()

    object Loading : ChoosingUserViewState()

    object NoData : ChoosingUserViewState()

    object ShowButtonTasks: ChoosingUserViewState()

    object HideButtonTasks: ChoosingUserViewState()

    class SnackBarErrorCommand(val messageResource: Int) : ChoosingUserViewState()
}
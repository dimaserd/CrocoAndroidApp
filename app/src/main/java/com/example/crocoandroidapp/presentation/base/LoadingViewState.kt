package com.example.crocoandroidapp.presentation.base

sealed class LoadingViewState {

    object Content : LoadingViewState()

    object Loading : LoadingViewState()

    object NoData : LoadingViewState()

    class SnackBarErrorCommand(val messageResource: Int) : LoadingViewState()
}
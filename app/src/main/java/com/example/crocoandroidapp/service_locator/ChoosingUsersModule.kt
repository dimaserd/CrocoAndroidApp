package com.example.crocoandroidapp.service_locator

import com.example.crocoandroidapp.presentation.choosing_users.viewmodel.ChoosingUsersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val choosingUsersModule = module {
    viewModel { ChoosingUsersViewModel(get(), get()) }
}

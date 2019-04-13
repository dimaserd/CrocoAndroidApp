package com.example.crocoandroidapp.service_locator

import com.example.crocoandroidapp.presentation.edit_profile.viewmodel.EditProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editProfileModule = module {
    viewModel { EditProfileViewModel(get(), get()) }
}

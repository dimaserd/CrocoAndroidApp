package com.example.crocoandroidapp.service_locator

import com.example.crocoandroidapp.presentation.profile.viewmodel.ProfileViewModel
import com.example.crocoandroidapp.utils.NetworkConstants
import com.example.data.network.UserApi
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UserUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val profileModule = module {
    single {
        with(Retrofit.Builder()) {
            baseUrl(NetworkConstants.URL)
            addConverterFactory(GsonConverterFactory.create(get()))
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            client(get())
            build()
        }.create(UserApi::class.java)
    }
    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory { UserUseCase(get()) }
    viewModel { ProfileViewModel(get(), get()) }
}

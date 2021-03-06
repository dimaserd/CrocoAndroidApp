package com.example.crocoandroidapp.service_locator

import com.example.crocoandroidapp.presentation.login.viewmodel.LoginViewModel
import com.example.crocoandroidapp.utils.NetworkConstants
import com.example.data.network.AuthApi
import com.example.data.repository.LoginRepositoryImpl
import com.example.domain.repository.LoginRepository
import com.example.domain.usecase.AuthUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val loginModule = module {
    single {
        with(Retrofit.Builder()) {
            baseUrl(NetworkConstants.URL)
            addConverterFactory(GsonConverterFactory.create(get()))
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            client(get())
            build()
        }.create(AuthApi::class.java)
    }
    factory<LoginRepository> { LoginRepositoryImpl(get(), get()) }
    factory { AuthUseCase(get()) }
    viewModel { LoginViewModel(get(), get()) }
}

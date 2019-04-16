package com.example.crocoandroidapp.service_locator

import com.example.crocoandroidapp.presentation.tasks.viewmodel.MonthsViewModel
import com.example.crocoandroidapp.utils.NetworkConstants
import com.example.data.network.TaskApi
import com.example.data.repository.TaskRepositoryImpl
import com.example.domain.repository.TaskRepository
import com.example.domain.usecase.TaskUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val taskModule = module {
    single {
        with(Retrofit.Builder()) {
            baseUrl(NetworkConstants.URL)
            addConverterFactory(GsonConverterFactory.create(get()))
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            client(get())
            build()
        }.create(TaskApi::class.java)
    }
    factory<TaskRepository> { TaskRepositoryImpl(get()) }
    factory { TaskUseCase(get()) }
    viewModel { MonthsViewModel(get(), get()) }
}

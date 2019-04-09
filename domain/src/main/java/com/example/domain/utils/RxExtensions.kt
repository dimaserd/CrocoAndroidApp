package com.example.domain.utils

import io.reactivex.Completable
import io.reactivex.Single

fun <T> Single<T>.schedulersIoToMain(schedulersProvider: SchedulersProvider): Single<T> =
    subscribeOn(schedulersProvider.io()).observeOn(schedulersProvider.mainThread())

fun Completable.schedulersIoToMain(schedulersProvider: SchedulersProvider): Completable =
    subscribeOn(schedulersProvider.io()).observeOn(schedulersProvider.mainThread())

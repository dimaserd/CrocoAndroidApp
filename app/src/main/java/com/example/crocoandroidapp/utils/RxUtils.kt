package com.example.crocoandroidapp.utils

import com.example.crocoandroidapp.R
import com.example.domain.utils.exceptions.UnauthorizedServerException
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.net.UnknownHostException

fun <T> Single<T>.smartSubscribe(
    onSuccess: (T) -> Unit,
    onUnauthorizedServerException: (() -> Unit)? = null,
    onError: ((Int) -> Unit)? = null
): Disposable {

    return subscribe({ onSuccess(it) }, {
        processError(
            it,
            onUnauthorizedServerException,
            onError
        )
    })
}

fun Completable.smartSubscribe(
    onSuccess: () -> Unit,
    onUnauthorizedServerException: (() -> Unit)? = null,
    onError: ((Int) -> Unit)? = null
): Disposable {

    return subscribe({ onSuccess() }, {
        processError(
            it,
            onUnauthorizedServerException,
            onError
        )
    })
}

private fun processError(
    throwable: Throwable,
    onUnauthorizedServerException: (() -> Unit)? = null,
    onError: ((Int) -> Unit)? = null
) {

    Timber.e(throwable)
    when (throwable) {
        is UnauthorizedServerException -> onUnauthorizedServerException?.invoke()
        is UnknownHostException -> onError?.invoke(R.string.no_internet)
        else -> onError?.invoke(R.string.unknown_error)
    }
}

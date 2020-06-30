package com.geo.mercadolibre.base.rx

import com.geo.mercadolibre.base.exception.HttpCallFailureException
import com.geo.mercadolibre.base.exception.NetworkException
import com.geo.mercadolibre.base.exception.NoNetworkException
import com.geo.mercadolibre.base.exception.ServerUnreachableException
import io.reactivex.Flowable
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun <T> Flowable<T>.mapNetworkErrors(): Flowable<T> =
    this.onErrorResumeNext {
            error: Throwable -> when (error) {
        is SocketTimeoutException -> Flowable.error(
            NoNetworkException(
                error
            )
        )
        is UnknownHostException -> Flowable.error(
            ServerUnreachableException(
                error
            )
        )
        is HttpException -> Flowable.error(
            HttpCallFailureException(
                error
            )
        )
        else -> Flowable.error(
            NetworkException(
                error
            )
        )
        }
    }
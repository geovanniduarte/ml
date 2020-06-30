package com.geo.mercadolibre.base.exception

import android.content.Context
import com.geo.mercadolibre.R
import retrofit2.HttpException

open class NetworkException(error: Throwable): RuntimeException(error) {
    open fun getCustomMessage(context: Context) : String = context.getString(R.string.error_unknown)
}

class NoNetworkException(error: Throwable): NetworkException(error) {
    override fun getCustomMessage(context: Context) : String = context.getString(R.string.error_no_network)
}

class ServerUnreachableException(error: Throwable): NetworkException(error) {
    override fun getCustomMessage(context: Context) : String = context.getString(R.string.error_no_conection)
}

class HttpCallFailureException(val error: HttpException): NetworkException(error) {
    override fun getCustomMessage(context: Context) : String {
        return error.message()
    }
}
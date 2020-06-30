package com.geo.mercadolibre.base.ViewModel

import com.geo.mercadolibre.base.exception.NetworkException

interface MVVMViewModel {

    fun handleApiError(error: Throwable?)

    fun setUserAsLoggedOut()
}
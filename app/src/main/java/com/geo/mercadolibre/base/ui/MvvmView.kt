package com.geo.mercadolibre.base.ui


interface MvvmView {
    fun showLoading()

    fun hideLoading()

    fun onError(resId: Int)

    fun onError(message: String?)

    fun showMessage(message: String?)

    fun showMessage(resId: Int)

    fun hideKeyboard()
}
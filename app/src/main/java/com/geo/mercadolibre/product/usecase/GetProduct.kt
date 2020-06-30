package com.geo.mercadolibre.product.usecase

import com.geo.mercadolibre.base.rx.mapNetworkErrors
import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import com.geo.mercadolibre.product.data.repository.ProductRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetProduct @Inject constructor(private val productRepositoryImpl: ProductRepositoryImpl) {
    fun execute(productId: String, compositeDisposable: CompositeDisposable, onSuccess: (value: ProductEntity) -> Unit, onError: (t: Throwable) -> Unit = {}) =
        productRepositoryImpl.getProduct(productId)
            .mapNetworkErrors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = onSuccess)
            .addTo(compositeDisposable)
}
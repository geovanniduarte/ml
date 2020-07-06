package com.geo.mercadolibre.product.usecase


import com.geo.mercadolibre.base.rx.mapNetworkErrors
import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import com.geo.mercadolibre.product.data.repository.ProductRepositoryImpl
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class GetProductList @Inject constructor(private val productRepositoryImpl: ProductRepositoryImpl, @Named("ioScheduler") private val ioScheduler: Scheduler, @Named("mainScheduler") private val mainScheduler: Scheduler) {

    fun buildCase(query: String): Flowable<List<ProductEntity>> =
        productRepositoryImpl.getProductList(query)
        .mapNetworkErrors()


    fun execute(query:String, compositeDisposable: CompositeDisposable, onSuccess: (value: List<ProductEntity>) -> Unit, onError: (t: Throwable) -> Unit = {}) {
            buildCase(query)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribeBy(onNext = onSuccess, onError = onError)
                .addTo(compositeDisposable)
    }
}
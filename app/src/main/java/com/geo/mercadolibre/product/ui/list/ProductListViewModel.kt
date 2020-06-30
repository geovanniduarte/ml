package com.geo.mercadolibre.product.ui.list

import androidx.lifecycle.MutableLiveData
import com.geo.mercadolibre.base.Constants.Companion.SEARCH_DEBOUNCE_TIME
import com.geo.mercadolibre.base.ViewModel.BaseViewModel
import com.geo.mercadolibre.product.data.model.entity.ProductEntity

import com.geo.mercadolibre.product.usecase.GetProductList
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named


class ProductListViewModel @Inject constructor(private val getProductList: GetProductList, @Named("mainScheduler") private val mainScheduler: Scheduler): BaseViewModel() {

    val productListState: MutableLiveData<List<ProductEntity>> = MutableLiveData()
    val newProductListState: MutableLiveData<List<ProductEntity>> = MutableLiveData()
    private val autoCompletePublishSubject = PublishSubject.create<String>()

    init {
        initSearch()
    }

    private var currentPage = 0

    fun loadProducts(query: String) {
        isLoadingState.value = true
        getProductList.execute(query, compositeDisposable,  onSuccess = {
            productListState.value = it
            isLoadingState.value = false
            currentPage = 1
        },
        onError = {
            handleApiError(it)
            isLoadingState.value = false
        })
    }

    fun loadMoreProducts(query: String) {
        isLoadingState.value = true
        currentPage++
        getProductList.execute(query, compositeDisposable,  onSuccess = {
            newProductListState.value = it
            isLoadingState.value = false
        },
            onError = {
                handleApiError(it)
                isLoadingState.value = false
            })
    }

    private fun initSearch() {
        autoCompletePublishSubject
            .debounce(SEARCH_DEBOUNCE_TIME , TimeUnit.SECONDS)
            .filter { it.isNotEmpty() && it.length >= 3 }
            .distinctUntilChanged()
            .map { it.toLowerCase().trim() }
            .observeOn(mainScheduler)
            .subscribe {
                loadProducts(it)
            }
            .addTo(compositeDisposable)
    }

    fun loadProductsSearch(query: String) {
        autoCompletePublishSubject.onNext(query)
    }

}
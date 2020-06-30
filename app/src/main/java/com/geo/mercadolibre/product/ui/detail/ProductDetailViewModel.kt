package com.geo.mercadolibre.product.ui.detail

import androidx.lifecycle.MutableLiveData
import com.geo.mercadolibre.base.ViewModel.BaseViewModel
import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import com.geo.mercadolibre.product.usecase.GetProduct
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(val getProduct: GetProduct) : BaseViewModel() {

    val productState: MutableLiveData<ProductEntity> = MutableLiveData()

    fun queryProduct(productId: String?) {
        if (productId != null) {
            isLoadingState.value = true
            getProduct.execute(productId, compositeDisposable, {
                productState.value = it
                isLoadingState.value = false
            },
            {
                handleApiError(it)
                isLoadingState.value = false
            })
        }
    }
}

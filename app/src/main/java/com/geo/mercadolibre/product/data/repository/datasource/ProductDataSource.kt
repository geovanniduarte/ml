package com.geo.mercadolibre.product.data.repository.datasource

import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import io.reactivex.Flowable

interface ProductDataSource {
    fun getProductList(query: String): Flowable<List<ProductEntity>>
}
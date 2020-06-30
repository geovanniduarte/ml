package com.geo.mercadolibre.product.data.repository

import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface ProductRepository {
    fun getProductList(query: String): Flowable<List<ProductEntity>>
    fun getProduct(productId: String): Flowable<ProductEntity>
}
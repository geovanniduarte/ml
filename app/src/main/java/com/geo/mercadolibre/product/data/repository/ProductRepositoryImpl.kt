package com.geo.mercadolibre.product.data.repository

import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import com.geo.mercadolibre.product.data.repository.datasource.ApiDataSource
import io.reactivex.Flowable

class ProductRepositoryImpl(val apiDatasource: ApiDataSource): ProductRepository {
    override fun getProductList(query: String): Flowable<List<ProductEntity>> = apiDatasource.getProductList(query)
    override fun getProduct(productId: String): Flowable<ProductEntity> = Flowable.just(
        ProductEntity("1ID", "TITLE TEST", 0.0, "NEW", "http://mla-s2-p.mlstatic.com/777036-MLA40451510582_012020-I.jpg", 0, 0, false)
    )
}
package com.geo.mercadolibre.product.data.repository.datasource

import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import com.geo.mercadolibre.product.data.mapper.ProductEntityMapper
import com.geo.mercadolibre.product.data.net.ProductService
import com.geo.mercadolibre.product.util.SiteManager
import io.reactivex.Flowable

class ApiDataSource(private val productService: ProductService, private val siteManager: SiteManager, private val productEntityMapper: ProductEntityMapper) : ProductDataSource {
    override fun getProductList(query: String): Flowable<List<ProductEntity>> =
        productService.getProducs(site = siteManager.currentSite, query = query)
            .map { it.results }
            .map { productEntityMapper.transformList(it) }
}
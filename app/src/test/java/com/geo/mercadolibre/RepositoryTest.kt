package com.geo.mercadolibre

import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import com.geo.mercadolibre.product.data.repository.ProductRepositoryImpl
import com.geo.mercadolibre.product.data.repository.datasource.ApiDataSource
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RepositoryTest {

    lateinit var productRepositoryImpl: ProductRepositoryImpl
    var mockApiDataSource: ApiDataSource = mock()
    @Before
    fun setUp() {
        productRepositoryImpl = ProductRepositoryImpl(mockApiDataSource)
    }

    @Test
    fun `test repository should give product list`() {
        val products = listOf(
                ProductEntity("1ID", "TITLE TEST", 0.0, "NEW", "http://mla-s2-p.mlstatic.com/777036-MLA40451510582_012020-I.jpg", 0, 0, false))

        val obs = Flowable.just(products)
        whenever(mockApiDataSource.getProductList(any())).thenReturn(obs)

        val result = productRepositoryImpl.getProductList("")

        result.test()
            .assertValue { it.size == 1 }
            .assertValue {
                it.first().price == products.first().price }
            .assertValue { it.first().id == products.first().id }
    }
}

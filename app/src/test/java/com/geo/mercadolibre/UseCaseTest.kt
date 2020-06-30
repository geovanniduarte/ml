package com.geo.mercadolibre

import com.geo.mercadolibre.base.exception.HttpCallFailureException
import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import com.geo.mercadolibre.product.data.repository.ProductRepositoryImpl
import com.geo.mercadolibre.product.usecase.GetProductList
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import retrofit2.HttpException


class UseCaseTest {

    var productRepositoryImpl: ProductRepositoryImpl = mock()
    lateinit var getProductList: GetProductList
    lateinit var testScheduler: TestScheduler
    lateinit var compositeDisposable: CompositeDisposable

    @Before
    fun setUp() {
        testScheduler = TestScheduler()
        compositeDisposable = CompositeDisposable()
        getProductList = GetProductList(productRepositoryImpl, testScheduler, TestScheduler())
    }

    @Test
    fun `when product list use casee executed then same list should be acquared`() {
        val products = listOf(
            ProductEntity("1ID", "TITLE TEST", 0.0, "NEW", "http://mla-s2-p.mlstatic.com/777036-MLA40451510582_012020-I.jpg", 0, 0, false)
        )
        val obs = Flowable.just(products)
        whenever(productRepositoryImpl.getProductList(any())).thenReturn(obs)

        getProductList.buildCase("test")
            .test()
            .assertValue {
                it.first().id == products.first().id }
            .assertValue { it.first().price == products.first().price }

    }

    @Test
    fun `when error is throwed error type should be the expected`() {

        val exception : HttpException = HttpException(mock())
        `when`(productRepositoryImpl.getProductList(any())).thenReturn(Flowable.error(exception))

        getProductList.buildCase("test")
            .test()
            .assertError{
                it is HttpCallFailureException
            }
    }
}
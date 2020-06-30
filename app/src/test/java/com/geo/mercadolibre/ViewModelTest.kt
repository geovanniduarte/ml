package com.geo.mercadolibre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import com.geo.mercadolibre.product.ui.list.ProductListViewModel
import com.geo.mercadolibre.product.usecase.GetProductList
import com.nhaarman.mockito_kotlin.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.CoreMatchers.any
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class ViewModelTest {
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var listViewModel: ProductListViewModel
    private var getListUseCase : GetProductList = mock()
    lateinit var testScheduler: TestScheduler
    var isLoadingObserver: Observer<Boolean> = mock()
    var dataObserver: Observer<List<ProductEntity>> = mock()
    var compositeDisposable: CompositeDisposable = mock()

    @Before
    fun setUp() {
        testScheduler = TestScheduler()
        listViewModel = ProductListViewModel(getListUseCase, testScheduler)
        listViewModel.isLoadingState.observeForever(isLoadingObserver)
        listViewModel.productListState.observeForever(dataObserver)
    }

    @Test
    fun `test view model should call show loading when search is performed`() {

        val products = listOf(
            ProductEntity("1ID", "TITLE TEST", 0.0, "NEW", "http://mla-s2-p.mlstatic.com/777036-MLA40451510582_012020-I.jpg", 0, 0, false)
        )

        whenever(getListUseCase.execute(eq("test"), any(), any(), any())).thenAnswer {
            val callback: (value: List<ProductEntity>) -> Unit = it.getArgument(2)
            callback.invoke(products)
        }

        listViewModel.loadProducts("test")

        verify(isLoadingObserver).onChanged(true)
        verify(isLoadingObserver).onChanged(false)
        verify(dataObserver).onChanged(products)
    }

}
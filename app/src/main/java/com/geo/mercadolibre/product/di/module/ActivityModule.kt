package com.geo.mercadolibre.product.di.module

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.geo.mercadolibre.base.di.ActivityContext
import com.geo.mercadolibre.base.di.PerActivity
import com.geo.mercadolibre.product.data.repository.ProductRepositoryImpl
import com.geo.mercadolibre.product.usecase.GetProduct
import com.geo.mercadolibre.product.usecase.GetProductList
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named


@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityContext
    @PerActivity
    fun provideActivityContext(): Activity = activity

    @Provides
    @PerActivity
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @PerActivity
    fun provideGetProductListUseCase(productRepositoryImpl: ProductRepositoryImpl, @Named("ioScheduler") ioScheduler: Scheduler, @Named("mainScheduler") mainScheduler: Scheduler) : GetProductList
            = GetProductList(productRepositoryImpl, ioScheduler, mainScheduler)



    @Provides
    @PerActivity
    fun provideGetProductUseCase(productRepositoryImpl: ProductRepositoryImpl): GetProduct
            = GetProduct(productRepositoryImpl)

}
package com.geo.mercadolibre.product.di.component

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.geo.mercadolibre.base.di.ApplicationContext
import com.geo.mercadolibre.product.data.repository.ProductRepositoryImpl
import com.geo.mercadolibre.product.di.module.ApplicationModule
import com.geo.mercadolibre.product.di.module.DataModule
import com.geo.mercadolibre.product.di.module.NetModule
import com.geo.mercadolibre.product.di.module.ViewModelModule
import com.geo.mercadolibre.product.util.Navigator
import dagger.Component
import io.reactivex.Scheduler
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun getContext(): Context
    fun getProductListRepository(): ProductRepositoryImpl
    fun getNavigator(): Navigator
    fun getViewModelFactory(): ViewModelProvider.Factory
    @Named("mainScheduler")
    fun getMainScheduler(): Scheduler
    @Named("ioScheduler")
    fun getIOScheduler(): Scheduler
}
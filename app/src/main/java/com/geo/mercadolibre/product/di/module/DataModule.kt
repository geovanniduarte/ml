package com.geo.mercadolibre.product.di.module

import android.content.Context
import android.content.SharedPreferences
import com.geo.mercadolibre.base.Constants
import com.geo.mercadolibre.product.data.mapper.ProductEntityMapper
import com.geo.mercadolibre.product.data.net.ProductService
import com.geo.mercadolibre.product.data.repository.ProductRepositoryImpl
import com.geo.mercadolibre.product.data.repository.datasource.ApiDataSource
import com.geo.mercadolibre.product.util.SiteManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideProductEntityMapper(): ProductEntityMapper = ProductEntityMapper()

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(Constants.ML_PREFS, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSiteManager(preferences: SharedPreferences) : SiteManager = SiteManager(preferences)

    @Singleton
    @Provides
    fun provideApiDataSource(productService: ProductService, siteManager: SiteManager, productEntityMapper: ProductEntityMapper) : ApiDataSource = ApiDataSource(productService, siteManager, productEntityMapper)

    @Singleton
    @Provides
    fun provideProductRepository(apiDataSource: ApiDataSource): ProductRepositoryImpl = ProductRepositoryImpl(apiDataSource)
}
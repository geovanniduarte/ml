package com.geo.mercadolibre.product.di.module

import com.geo.mercadolibre.BuildConfig
import com.geo.mercadolibre.product.data.net.ProductService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit) = retrofit.create(ProductService::class.java)

}
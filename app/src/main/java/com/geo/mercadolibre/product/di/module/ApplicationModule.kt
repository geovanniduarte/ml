package com.geo.mercadolibre.product.di.module

import android.app.Application
import android.content.Context
import com.geo.mercadolibre.base.MLApp
import com.geo.mercadolibre.product.util.Navigator
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: MLApp) {

    @Provides
    @Singleton
    fun provideApp(): Application = app

    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideNavigator() = Navigator()

    @Provides
    @Singleton
    @Named("ioScheduler")
    fun providesIOScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    @Named("mainScheduler")
    fun providesMainScheduler(): Scheduler = AndroidSchedulers.mainThread()
}
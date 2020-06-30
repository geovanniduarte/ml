package com.geo.mercadolibre.base

import android.app.Application
import com.geo.mercadolibre.product.di.component.ApplicationComponent
import com.geo.mercadolibre.product.di.component.DaggerApplicationComponent
import com.geo.mercadolibre.product.di.module.ApplicationModule

private var mApplicationComponent: ApplicationComponent? = null

class MLApp : Application() {

    override fun onCreate() {
        super.onCreate()
        mApplicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

    }

    public fun getComponent() = mApplicationComponent

}
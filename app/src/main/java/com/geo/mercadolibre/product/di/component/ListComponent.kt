package com.geo.mercadolibre.product.di.component

import com.geo.mercadolibre.base.di.PerActivity
import com.geo.mercadolibre.product.di.module.ActivityModule
import com.geo.mercadolibre.product.ui.detail.ProductDetailFragment
import com.geo.mercadolibre.product.ui.list.ListActivity
import com.geo.mercadolibre.product.ui.list.ProductListFragment
import dagger.Component

@PerActivity
@Component(modules = [ActivityModule::class], dependencies = [ApplicationComponent::class])
interface ListComponent {
    fun inject(activity: ListActivity)
    fun inject(listFragment: ProductListFragment)
    fun inject(detailFragment: ProductDetailFragment)
}
package com.geo.mercadolibre.product.util

import android.os.Build
import androidx.transition.Fade
import android.view.View
import androidx.fragment.app.FragmentManager
import com.geo.mercadolibre.R
import com.geo.mercadolibre.base.Constants.Companion.IMAGE_DETAIL_TRANSITION_NAME
import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import com.geo.mercadolibre.product.ui.detail.DetailsTransition
import com.geo.mercadolibre.product.ui.detail.ProductDetailFragment
import com.geo.mercadolibre.product.ui.list.ProductListFragment


class Navigator constructor() {

    fun installChangeProductListFragmentWith(supportFragmentManager: FragmentManager, query: String) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.root_fragment)
        if (currentFragment == null) {
            val productListFragment = ProductListFragment.newInstance(query)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.root_fragment, productListFragment, "productslist")
                .addToBackStack(null)
                .commit()
        } else if(currentFragment is ProductListFragment) {
            currentFragment.updateQuery(query)
        }
    }

    fun uninstallProductListFragmentIn(supportFragmentManager: FragmentManager) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.root_fragment)
        if (currentFragment is ProductListFragment) {
            supportFragmentManager.popBackStack()
        }
    }

    fun navigatesToDetail(supportFragmentManager: FragmentManager, productEntity: ProductEntity, imageView: View) {
        val details: ProductDetailFragment = ProductDetailFragment.newInstance(productEntity)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            details.sharedElementEnterTransition = DetailsTransition()
            details.enterTransition = Fade()
            details.exitTransition = Fade()
            details.sharedElementReturnTransition = DetailsTransition()
        }

        supportFragmentManager
            .beginTransaction()
            .addSharedElement(imageView, IMAGE_DETAIL_TRANSITION_NAME)
            .replace(R.id.root_fragment, details)
            .addToBackStack(null)
            .commit()
    }
}
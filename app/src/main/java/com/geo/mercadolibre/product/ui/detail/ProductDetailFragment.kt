package com.geo.mercadolibre.product.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

import com.geo.mercadolibre.R
import com.geo.mercadolibre.base.Constants.Companion.IMAGE_DETAIL_TRANSITION_NAME
import com.geo.mercadolibre.base.ui.BaseFragment
import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import kotlinx.android.synthetic.main.fragment_product_detail.*
import javax.inject.Inject

private const val ARG_PRODUCT = "product"

class ProductDetailFragment : BaseFragment() {

    companion object {
        fun newInstance(product: ProductEntity) = ProductDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PRODUCT, product)
            }
        }
    }

    private var product: ProductEntity? = null

    private lateinit var viewModel: ProductDetailViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inject()
        val view =  inflater.inflate(R.layout.fragment_product_detail, container, false)
        arguments?.let {
            product = it.getParcelable(ARG_PRODUCT)
        }
        return view
    }

    private fun inject() {
        activityComponent?.inject(this)
    }

    override fun setUp(view: View?) {
        ViewCompat.setTransitionName(image_detail, IMAGE_DETAIL_TRANSITION_NAME)
        setUpViewModel()
        hideKeyboard()
        product?.let {
            syncView(it)
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductDetailViewModel::class.java)
    }


    private fun syncView(productEntity: ProductEntity) {
        Glide.with(this.context!!)
            .asBitmap()
            .load(productEntity.thumbnail)
            .into(image_detail)

        txt_detail_condition.text = "${productEntity.condition} - ${productEntity.soldQuantity} ${getString(R.string.sold_detail_word)}"
        txt_detail_title.text = productEntity.title
        txt_detail_price.text = "$ ${productEntity.price}"
        val stock = if (productEntity.availableQuantity > 0) getString(R.string.stock_with_detail) else getString(R.string.stock_out_detail)
        txt_detail_availability.text = stock
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}

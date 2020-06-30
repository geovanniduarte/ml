package com.geo.mercadolibre.product.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geo.mercadolibre.R
import com.geo.mercadolibre.base.ui.BaseFragment
import com.geo.mercadolibre.product.data.model.entity.ProductEntity
import com.geo.mercadolibre.product.di.component.ListComponent
import com.geo.mercadolibre.product.util.Navigator
import kotlinx.android.synthetic.main.fragment_product_list.*
import javax.inject.Inject

private const val ARG_QUERY = "query"

class ProductListFragment : BaseFragment() {

    private var query: String = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Navigator

    lateinit var viewModel: ProductListViewModel

    private val adapter =
        ProductListAdapter { product, image ->
            onProductClicked(product, image)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        arguments?.let {
            query = it.getString(ARG_QUERY, "")
        }
    }

    private fun inject() {
        activityComponent?.inject(this)
    }

    override fun setUp(view: View?) {
        setUpViewModel()
        setUpProductList()
        setUpView()
        bindEvents()
        viewModel.loadProductsSearch(query)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this.activity!!, viewModelFactory).get(ProductListViewModel::class.java)
    }

    private fun setUpProductList() {
        product_list.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        product_list.itemAnimator = DefaultItemAnimator()
        product_list.adapter = adapter

        product_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMoreProducts(query)
                }
            }
        })
    }

    private fun setUpView() {
        button_reload.setOnClickListener {
            viewModel.loadProducts(query)
        }
    }

    private fun resetView() {
        button_reload.visibility = View.GONE
        message.text = ""
    }

    private fun bindEvents() {
        viewModel.productListState.observe(this, Observer {
            populateProductList(it, false)
        })

        viewModel.newProductListState.observe(this, Observer {
            populateProductList(it, true)
        })

        viewModel.errorMessageState.observe(this, Observer { message ->
            onError(message)
        })
    }

    override fun onError(message: String?) {
        adapter.swapData(listOf())
        showMessage(message)
        button_reload.visibility = View.VISIBLE
    }

    override fun showMessage(messageStr: String?) {
        message.text = messageStr
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        val component: ListComponent? = activityComponent
        component?.let {
            component.inject(this)
        }
        return view
    }

    private fun populateProductList(products: List<ProductEntity>, add: Boolean) {
        resetView()
        if (add) {
            adapter.swapDataAdd(products)
        } else {
            adapter.swapData(products)
        }
        if (products.isEmpty()) {
             showMessage(getString(R.string.error_empty_list))
        }
    }

    private fun onProductClicked(productEntity: ProductEntity, imageView: View) {
        navigator.navigatesToDetail(this.activity!!.supportFragmentManager, productEntity, imageView)
    }

    fun updateQuery(query: String) {
        this.query = query
        viewModel.loadProductsSearch(query)
    }

    companion object {
        @JvmStatic
        fun newInstance(query: String) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUERY, query)
                }
            }
    }
}

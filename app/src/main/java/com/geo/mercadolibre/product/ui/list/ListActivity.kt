package com.geo.mercadolibre.product.ui.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.geo.mercadolibre.R
import com.geo.mercadolibre.base.ui.BaseActivity
import com.geo.mercadolibre.product.util.Navigator
import kotlinx.android.synthetic.main.activity_list.*
import javax.inject.Inject

class ListActivity : BaseActivity(),
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    private lateinit var searchView: SearchView

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Navigator

    lateinit var viewModel: ProductListViewModel

    private var query: String = ""

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.run {
            putString(QUERY, query)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.run {
            query = getString(QUERY, "")
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list, menu)
        searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)
        searchView.setQuery(query, true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> {
            supportFragmentManager.popBackStack()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun inject() {
        mUIComponent?.inject(this)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductListViewModel::class.java)
        bindEvents()
    }

    private fun bindEvents() {
        viewModel.isLoadingState.observe(this, Observer { isLoading ->
            if (isLoading) showLoading() else hideLoading()
        })
    }

    override fun showLoading() {
        //super.showLoading()
        list_progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        //super.hideLoading()
        list_progress_bar.visibility = View.GONE
    }

    //OnQueryTextListener
    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            navigator.installChangeProductListFragmentWith(supportFragmentManager, it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            query = newText
            if (it.isEmpty()) {
                navigator.uninstallProductListFragmentIn(supportFragmentManager)
                hideKeyboard()
            } else {
                navigator.installChangeProductListFragmentWith(supportFragmentManager, it)
            }
        }
        return true
    }

    //OnCloseListener
    override fun onClose(): Boolean {
        query = ""
        navigator.uninstallProductListFragmentIn(supportFragmentManager)
        return false
    }

    companion object {
        val QUERY = "query_string"
    }
}

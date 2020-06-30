package com.geo.mercadolibre.base.ui


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.geo.mercadolibre.product.di.component.ListComponent


abstract class BaseFragment : Fragment(), MvvmView {
    var baseActivity: BaseActivity? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseActivity) {
            baseActivity = context
        }

        if (context is BaseFragment.Callback) {
            context.onFragmentAttached()
        }
    }

    override fun showLoading() {
       baseActivity?.let {
           it.showLoading()
       }
    }

    override fun hideLoading() {
        baseActivity?.let {
            it.hideLoading()
        }
    }

    override fun onError(message: String?) {
        baseActivity?.let {
            it.onError(message)
        }
    }

    override fun onError(@StringRes resId: Int) {
        baseActivity?.let {
            it.onError(resId)
        }
    }

    override fun showMessage(message: String?) {
        baseActivity?.let {
            it.showMessage(message)
        }
    }

    override fun showMessage(@StringRes resId: Int) {
        baseActivity?.let {
            it.showMessage(resId)
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun hideKeyboard() {
        baseActivity?.let {
            it.hideKeyboard()
        }
    }


    val activityComponent: ListComponent?
        get() {
            return if (baseActivity != null) {
                baseActivity!!.getActivityComponent()
            } else null
        }


    protected abstract fun setUp(view: View?)

    override fun onDestroy() {
        super.onDestroy()
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String?)
    }
}
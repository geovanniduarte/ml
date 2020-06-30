package com.geo.mercadolibre.base.ui


import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.geo.mercadolibre.R
import com.geo.mercadolibre.base.MLApp
import com.geo.mercadolibre.product.di.component.DaggerListComponent
import com.geo.mercadolibre.product.di.component.ListComponent
import com.geo.mercadolibre.product.di.module.ActivityModule
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity: AppCompatActivity(), MvvmView, BaseFragment.Callback {
    private var mProgressDialog: AlertDialog? = null

    var mUIComponent: ListComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUIComponent = DaggerListComponent.builder()
            .activityModule(ActivityModule(this))
            .applicationComponent(( application as MLApp).getComponent())
            .build()

    }

    fun getActivityComponent(): ListComponent? {
        return mUIComponent
    }

    @TargetApi(Build.VERSION_CODES.M)
    open fun requestPermissionsSafely(
        permissions: Array<String?>?,
        requestCode: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    open fun hasPermission(permission: String?): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    override fun showLoading() {
        hideLoading()
        mProgressDialog = AlertDialog.Builder(this)
            .setView(R.layout.dialog_wait)
            .create()
        mProgressDialog?.show()
    }

    override fun hideLoading() {
        mProgressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    override fun onError(message: String?) {
        if (message != null) {
            showSnackBar(message)
        } else {
            showSnackBar(getString(R.string.error_unknown))
        }
    }

    override fun onError(resId: Int) {
        onError(getString(resId))
    }

    open fun showSnackBar(message: String) {
        val snackbar: Snackbar = Snackbar.make(
            findViewById<View>(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        )
        snackbar.show()
    }

    override fun showMessage(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()
        }
    }

    override fun showMessage(@StringRes resId: Int) {
        showMessage(getString(resId))
    }

    override fun hideKeyboard() {
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    // BaseFragment callback
    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String?) {

    }
}
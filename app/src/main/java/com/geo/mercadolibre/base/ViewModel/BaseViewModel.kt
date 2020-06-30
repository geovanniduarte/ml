package com.geo.mercadolibre.base.ViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geo.mercadolibre.R
import com.geo.mercadolibre.base.di.ApplicationContext
import com.geo.mercadolibre.base.exception.NetworkException
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel : ViewModel(),
    MVVMViewModel {

    val isLoadingState: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessageState : MutableLiveData<String> = MutableLiveData()

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var context: Context

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    override fun handleApiError(error: Throwable?) {

        if (error != null && error is NetworkException) {
            errorMessageState.value = error.getCustomMessage(context)
        } else {
            errorMessageState.value = context.getString(R.string.error_unknown)
        }
    }

    override fun setUserAsLoggedOut() {
        TODO("Not yet implemented")
    }

}
package com.oqurystudio.karanel.android.base

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.*
import com.oqurystudio.karanel.android.network.NetworkRequestType
import com.oqurystudio.karanel.android.network.ViewState
import com.oqurystudio.karanel.android.util.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

abstract class BaseViewModel : ViewModel() {

    private val _viewState = MutableLiveData<Pair<ViewState, NetworkRequestType>>()
    val viewState: LiveData<Pair<ViewState, NetworkRequestType>> = _viewState

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun <T> requestAPI(
        data: MutableLiveData<T>,
        requestType: NetworkRequestType,
        viewStateActive: Boolean = true,
        apiMethod: suspend () -> T
    ) {
        viewModelScope.launch {
            if (viewStateActive) _viewState.postValue(Pair(ViewState.LOADING, requestType))
            withContext(Dispatchers.IO) {
                try {
                    val result = apiMethod()
                    data.postValue(result)
                    if (viewStateActive) _viewState.postValue(Pair(ViewState.SUCCESS, requestType))
                } catch (throwable: Throwable) {
                    handleNetworkError(throwable)
                    if (viewStateActive) _viewState.postValue(Pair(ViewState.ERROR, requestType))
                }
            }
        }
    }

    fun <T> requestAPIWithToken(
        dataStore: DataStoreManager,
        data: MutableLiveData<T>,
        requestType: NetworkRequestType,
        viewStateActive: Boolean = true,
        apiMethod: suspend (token: String) -> T
    ) {
        viewModelScope.launch {
            dataStore.readValue(stringPreferencesKey(DataStoreManager.TOKEN), "") {
                viewModelScope.launch {
                    if (viewStateActive) _viewState.postValue(Pair(ViewState.LOADING, requestType))
                    withContext(Dispatchers.IO) {
                        try {
                            val result = apiMethod(this@readValue)
                            data.postValue(result)
                            if (viewStateActive) _viewState.postValue(Pair(ViewState.SUCCESS, requestType))
                        } catch (throwable: Throwable) {
                            handleNetworkError(throwable)
                            if (viewStateActive) _viewState.postValue(Pair(ViewState.ERROR, requestType))
                        }
                    }
                }
            }
        }
    }

    private fun handleNetworkError(throwable: Throwable) {
        val message: String = when (throwable) {
            is IOException -> {
                "Network Error"
            }
            is HttpException -> {
                val code = throwable.code()
                val errorResponse = throwable.message()
                "Error $code $errorResponse"
            }
            else -> {
                "Unknown Error"
            }
        }
        _error.postValue(message)
    }
}
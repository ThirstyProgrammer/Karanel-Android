package com.oqurystudio.karanel.android.ui

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var dataStore: DataStoreManager,
    private val repo: KaranelRepository
) : BaseViewModel() {

    private val _isLogin: MutableLiveData<Boolean> = MutableLiveData()
    val isLogin: MutableLiveData<Boolean> = _isLogin

    fun getAuth() {
        viewModelScope.launch {
            dataStore.readValue(booleanPreferencesKey("is_login"), false) {
                _isLogin.postValue(this)
            }
        }
    }

    fun saveAuth() {
        viewModelScope.launch {
            dataStore.storeValue(booleanPreferencesKey("is_login"), true)
        }
    }
}
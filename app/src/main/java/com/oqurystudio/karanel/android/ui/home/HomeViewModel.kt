package com.oqurystudio.karanel.android.ui.home

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.model.DashboardPosyandu
import com.oqurystudio.karanel.android.network.NetworkRequestType
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var dataStore: DataStoreManager,
    private val repo: KaranelRepository
) : BaseViewModel() {

    private val _response: MutableLiveData<DashboardPosyandu.Response> = MutableLiveData()
    val response: LiveData<DashboardPosyandu.Response> = _response

    private val _token: MutableLiveData<String> = MutableLiveData()
    val token: LiveData<String> = _token

    fun getToken() {
        viewModelScope.launch {
            dataStore.readValue(stringPreferencesKey(DataStoreManager.TOKEN), "") {
                _token.postValue(this)
            }
        }
    }

    fun getDashboardData(token: String) {
        requestAPI(_response, NetworkRequestType.DASHBOARD) {
            repo.getDashboardPosyandu(token)
        }
    }

    fun signOut(): Job = viewModelScope.launch {
        dataStore.clearUserPreference()
    }
}
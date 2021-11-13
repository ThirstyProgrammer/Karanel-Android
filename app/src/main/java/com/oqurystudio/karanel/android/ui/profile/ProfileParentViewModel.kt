package com.oqurystudio.karanel.android.ui.profile

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.model.DashboardPosyandu
import com.oqurystudio.karanel.android.model.Parent
import com.oqurystudio.karanel.android.network.NetworkRequestType
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileParentViewModel @Inject constructor(
    private var dataStore: DataStoreManager,
    private val repo: KaranelRepository
) : BaseViewModel() {

    private val _token: MutableLiveData<String> = MutableLiveData()
    val token: LiveData<String> = _token

    fun getToken() {
        viewModelScope.launch {
            dataStore.readValue(stringPreferencesKey(DataStoreManager.TOKEN), "") {
                _token.postValue(this)
            }
        }
    }

    private val _response: MutableLiveData<Parent.Response> = MutableLiveData()
    val response: LiveData<Parent.Response> = _response

    fun getParent(token: String) {
        requestAPI(_response, NetworkRequestType.PARENT) {
            repo.getParentByToken(token)
        }
    }

    fun signOut(): Job = viewModelScope.launch {
        dataStore.clearUserPreference()
    }
}
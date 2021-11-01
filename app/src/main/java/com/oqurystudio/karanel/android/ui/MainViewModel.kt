package com.oqurystudio.karanel.android.ui

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.model.UserType
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
    val isLogin: LiveData<Boolean> = _isLogin

    private val _userType: MutableLiveData<UserType> = MutableLiveData()
    val userType: LiveData<UserType> = _userType

    fun getUserType() {
        viewModelScope.launch {
            dataStore.readValue(stringPreferencesKey(DataStoreManager.USER_TYPE), "") {
                when (UserType.getEnum(this)) {
                    UserType.PARENT -> _userType.postValue(UserType.PARENT)
                    UserType.POSYANDU -> _userType.postValue(UserType.POSYANDU)
                }
            }
        }
    }

    fun getAuth() {
        viewModelScope.launch {
            dataStore.readValue(booleanPreferencesKey(DataStoreManager.IS_LOGIN), false) {
                _isLogin.postValue(this)
            }
        }
    }
}
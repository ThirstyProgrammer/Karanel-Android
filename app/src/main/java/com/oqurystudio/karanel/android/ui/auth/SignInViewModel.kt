package com.oqurystudio.karanel.android.ui.auth

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private var dataStore: DataStoreManager,
    private val repo: KaranelRepository
) : BaseViewModel() {

    private var email: String = ""
    private var password: String = ""

    private val _isSignInEnable: MutableLiveData<Boolean> = MutableLiveData()
    val isSignInEnable: MutableLiveData<Boolean> = _isSignInEnable

    fun updateEmail(email: String) {
        this.email = email
        if (isSignInEnable() != _isSignInEnable.value) {
            _isSignInEnable.postValue(isSignInEnable())
        }
    }

    fun updatePassword(password: String) {
        this.password = password
        if (isSignInEnable() != _isSignInEnable.value) {
            _isSignInEnable.postValue(isSignInEnable())
        }
    }

    fun signIn() {
        viewModelScope.launch {
            dataStore.storeValue(booleanPreferencesKey("is_login"), true)
        }
    }

    private fun isSignInEnable(): Boolean = email.isNotBlank() && password.isNotBlank()
}
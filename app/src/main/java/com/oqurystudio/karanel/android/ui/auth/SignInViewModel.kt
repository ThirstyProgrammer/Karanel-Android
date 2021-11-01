package com.oqurystudio.karanel.android.ui.auth

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.model.LoginParent
import com.oqurystudio.karanel.android.model.LoginPosyandu
import com.oqurystudio.karanel.android.model.UserType
import com.oqurystudio.karanel.android.network.NetworkRequestType
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
import com.oqurystudio.karanel.android.util.defaultDash
import com.oqurystudio.karanel.android.util.defaultEmpty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private var dataStore: DataStoreManager,
    private val repo: KaranelRepository
) : BaseViewModel() {

    private var payloadPosyandu: LoginPosyandu.Request = LoginPosyandu.Request()
    private var payloadParent: LoginParent.Request = LoginParent.Request()

    private val _users = MutableLiveData<LoginPosyandu.Response>()
    val users: LiveData<LoginPosyandu.Response> = _users

    private val _parent = MutableLiveData<LoginParent.Response>()
    val parent: LiveData<LoginParent.Response> = _parent

    private val _isSignInEnable: MutableLiveData<Boolean> = MutableLiveData()
    val isSignInEnable: MutableLiveData<Boolean> = _isSignInEnable

    fun updateEmail(email: String) {
        payloadPosyandu.email = email
        if (isSignInEnable() != _isSignInEnable.value) {
            _isSignInEnable.postValue(isSignInEnable())
        }
    }

    fun updatePassword(password: String) {
        payloadPosyandu.password = password
        if (isSignInEnable() != _isSignInEnable.value) {
            _isSignInEnable.postValue(isSignInEnable())
        }
    }

    fun updateIdParent(id: String) {
        payloadParent.idParent = id
        if (isSignInEnable() != _isSignInEnable.value) {
            _isSignInEnable.postValue(isSignInEnable())
        }
    }

    fun signInPosyandu() {
        requestAPI(_users, NetworkRequestType.LOGIN) {
            repo.loginPosyandu(payloadPosyandu)
        }
    }

    fun signInParent() {
        requestAPI(_parent, NetworkRequestType.LOGIN) {
            repo.loginParent(payloadParent)
        }
    }

    fun updateUserPreferences(data: LoginPosyandu.Data, userType: UserType): Job = viewModelScope.launch {
        dataStore.storeUserPreference(data, userType)
    }

    fun updateUserPreferences(data: LoginParent.Data, userType: UserType): Job = viewModelScope.launch {
        dataStore.storeUserPreference(data, userType)
    }

    private fun isSignInEnable(): Boolean =
        (payloadPosyandu.email.isNotBlank() && payloadPosyandu.password.isNotBlank()) || payloadParent.idParent.isNotBlank()
}
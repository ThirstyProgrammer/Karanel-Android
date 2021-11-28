package com.oqurystudio.karanel.android.ui.form

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.model.FormParent
import com.oqurystudio.karanel.android.model.Parent
import com.oqurystudio.karanel.android.network.NetworkRequestType
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
import com.oqurystudio.karanel.android.util.defaultEmpty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormParentViewModel @Inject constructor(
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

    var isEditParentData: Boolean = false
    val parentPayload = FormParent.Payload()

    private val _isFormParentCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val isFormParentCompleted: MutableLiveData<Boolean> = _isFormParentCompleted

    private val _responseGetParent: MutableLiveData<Parent.Response> = MutableLiveData()
    val responseGetParent: LiveData<Parent.Response> = _responseGetParent

    fun getParent(token: String) {
        requestAPI(_responseGetParent, NetworkRequestType.PARENT) {
            repo.getParentByToken(token)
        }
    }

    fun updateParentData() {
        // TODO API UPDATE PARENT DATA
    }

    fun updateParentPayload(data: Parent.Data?) {
        if (data != null) {
            parentPayload.motherNIK = data.motherNIK.defaultEmpty()
            parentPayload.motherName = data.motherName.defaultEmpty()
            parentPayload.motherPhone = data.motherPhone.defaultEmpty()
            parentPayload.motherWork = data.motherJob.defaultEmpty()
            parentPayload.fatherNIK = data.fatherNIK.defaultEmpty()
            parentPayload.fatherName = data.fatherName.defaultEmpty()
            parentPayload.fatherPhone = data.fatherPhone.defaultEmpty()
            parentPayload.fatherWork = data.fatherJob.defaultEmpty()
            parentPayload.address = data.address.defaultEmpty()
        }
    }

    fun updateFormParentState() {
        if (parentPayload.address.isBlank()) {
            _isFormParentCompleted.postValue(false)
            return
        }
        if (parentPayload.motherNIK.isBlank()) {
            _isFormParentCompleted.postValue(false)
            return
        }
        if (parentPayload.motherName.isBlank()) {
            _isFormParentCompleted.postValue(false)
            return
        }
        if (parentPayload.motherWork.isBlank()) {
            _isFormParentCompleted.postValue(false)
            return
        }
        if (parentPayload.motherPhone.isBlank()) {
            _isFormParentCompleted.postValue(false)
            return
        }
        if (parentPayload.fatherNIK.isBlank()) {
            _isFormParentCompleted.postValue(false)
            return
        }
        if (parentPayload.fatherName.isBlank()) {
            _isFormParentCompleted.postValue(false)
            return
        }
        if (parentPayload.fatherWork.isBlank()) {
            _isFormParentCompleted.postValue(false)
            return
        }
        if (parentPayload.fatherPhone.isBlank()) {
            _isFormParentCompleted.postValue(false)
            return
        }
        _isFormParentCompleted.postValue(true)
        return
    }
}
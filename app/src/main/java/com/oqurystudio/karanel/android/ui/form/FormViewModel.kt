package com.oqurystudio.karanel.android.ui.form

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.model.FormChild
import com.oqurystudio.karanel.android.model.FormParent
import com.oqurystudio.karanel.android.model.Parent
import com.oqurystudio.karanel.android.model.Parents
import com.oqurystudio.karanel.android.network.NetworkRequestType
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
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

    var parentId: String = ""
    val parentPayload = FormParent.Payload()

    private val _isFormParentCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val isFormParentCompleted: MutableLiveData<Boolean> = _isFormParentCompleted

    private val _responseSubmitParent: MutableLiveData<FormParent.Response> = MutableLiveData()
    val responseSubmitParent: LiveData<FormParent.Response> = _responseSubmitParent

    fun updateFormParentState() {
        if (parentPayload.nik.isBlank()) {
            _isFormParentCompleted.postValue(false)
            return
        }
        if (parentPayload.address.isBlank()) {
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

    fun submitParent(token: String) {
        requestAPI(_responseSubmitParent, NetworkRequestType.FORM_PARENT) {
            repo.submitParent(token, parentPayload)
        }
    }

    val childPayload = FormChild.Payload()

    private val _isFormChildCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val isFormChildCompleted: MutableLiveData<Boolean> = _isFormChildCompleted

    private val _responseSubmitChild: MutableLiveData<FormChild.Response> = MutableLiveData()
    val responseSubmitChild: LiveData<FormChild.Response> = _responseSubmitChild

    fun updateFormChildState() {
        if (childPayload.name.isBlank()) {
            _isFormChildCompleted.postValue(false)
            return
        }
        if (childPayload.birthPlace.isBlank()) {
            _isFormChildCompleted.postValue(false)
            return
        }
        if (childPayload.birthDate.isBlank()) {
            _isFormChildCompleted.postValue(false)
            return
        }
        if (childPayload.childOrder == 0) {
            _isFormChildCompleted.postValue(false)
            return
        }
        if (childPayload.record.week == 0) {
            _isFormChildCompleted.postValue(false)
            return
        }
        if (childPayload.record.weight == 0.0) {
            _isFormChildCompleted.postValue(false)
            return
        }
        if (childPayload.record.height == 0.0) {
            _isFormChildCompleted.postValue(false)
            return
        }
        if (childPayload.record.headCircumference == 0.0) {
            _isFormChildCompleted.postValue(false)
            return
        }
        _isFormChildCompleted.postValue(true)
        return
    }

    fun submitChild(token: String = this.token.value.toString(), parentId: String = this.parentId) {
        childPayload.parentId = parentId
        requestAPI(_responseSubmitChild, NetworkRequestType.FORM_CHILD) {
            repo.submitChild(token, childPayload)
        }
    }
}
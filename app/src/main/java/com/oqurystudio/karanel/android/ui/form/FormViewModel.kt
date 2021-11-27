package com.oqurystudio.karanel.android.ui.form

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.model.*
import com.oqurystudio.karanel.android.network.NetworkRequestType
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
import com.oqurystudio.karanel.android.util.defaultEmpty
import com.oqurystudio.karanel.android.util.defaultZero
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
    var childId: String = ""
    var parentCode: String = ""
    var parentImgUrl: String = ""
    val parentPayload = FormParent.Payload()

    private val _responseSubmitParent: MutableLiveData<FormParent.Response> = MutableLiveData()
    val responseSubmitParent: LiveData<FormParent.Response> = _responseSubmitParent

    fun updateParentPayload(payload: FormParent.Payload?) {
        if (payload != null) {
            parentPayload.motherNIK = payload.motherNIK
            parentPayload.motherName = payload.motherName
            parentPayload.motherPhone = payload.motherPhone
            parentPayload.motherWork = payload.motherWork
            parentPayload.fatherNIK = payload.fatherNIK
            parentPayload.fatherName = payload.fatherName
            parentPayload.fatherPhone = payload.fatherPhone
            parentPayload.fatherWork = payload.fatherWork
            parentPayload.address = payload.address
        }
    }

    fun updateChildPayload(data: Child.Data?) {
        if (data != null) {
            childPayload.nik = data.nik.defaultEmpty()
            childPayload.name = data.name.defaultEmpty()
            childPayload.gender = data.gender.defaultEmpty()
            childPayload.birthPlace = data.birthPlace.defaultEmpty()
            childPayload.birthDate = data.birthDate.defaultEmpty()
            childPayload.birthType = data.birthType ?: "Tunggal"
            childPayload.bloodType = data.blood.defaultEmpty()
            childPayload.childOrder = data.childOrder.defaultZero()
        }
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

    private val _responseGetChild: MutableLiveData<Child.Response> = MutableLiveData()
    val responseGetChild: LiveData<Child.Response> = _responseGetChild

    fun updateFormChildState() {
        if (childPayload.nik.isBlank()) {
            _isFormChildCompleted.postValue(false)
            return
        }
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
        if (childPayload.ageOfBirth == 0) {
            _isFormChildCompleted.postValue(false)
            return
        }
        if (childPayload.record.weight == 0.0 && childId.isBlank()) {
            _isFormChildCompleted.postValue(false)
            return
        }
        if (childPayload.record.height == 0.0 && childId.isBlank()) {
            _isFormChildCompleted.postValue(false)
            return
        }
        if (childPayload.record.headCircumference == 0.0 && childId.isBlank()) {
            _isFormChildCompleted.postValue(false)
            return
        }
        _isFormChildCompleted.postValue(true)
        return
    }

    fun getChild(token: String) {
        requestAPI(_responseGetChild, NetworkRequestType.PARENT) {
            repo.getChild(token, childId)
        }
    }

    fun submitChild(token: String = this.token.value.toString(), parentId: String = this.parentId) {
        childPayload.parentId = parentId
        requestAPI(_responseSubmitChild, NetworkRequestType.FORM_CHILD) {
            repo.submitChild(token, childPayload)
        }
    }

    fun submitChildAsParent(token: String) {
        requestAPI(_responseSubmitChild, NetworkRequestType.FORM_CHILD) {
            repo.submitChildAsParent(token, childPayload)
        }
    }

    fun updateChild(token: String = this.token.value.toString()) {
        requestAPI(_responseSubmitChild, NetworkRequestType.FORM_CHILD) {
            repo.updateChild(token, childId, childPayload)
        }
    }
}
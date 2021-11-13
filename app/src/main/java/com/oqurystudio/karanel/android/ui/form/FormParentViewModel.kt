package com.oqurystudio.karanel.android.ui.form

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.model.FormParent
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
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

    var parentId: String = ""
    val parentPayload = FormParent.Payload()

    private val _isFormParentCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val isFormParentCompleted: MutableLiveData<Boolean> = _isFormParentCompleted

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
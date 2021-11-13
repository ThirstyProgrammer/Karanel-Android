package com.oqurystudio.karanel.android.ui.child

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.model.FormChild
import com.oqurystudio.karanel.android.model.FormProgress
import com.oqurystudio.karanel.android.model.Parent
import com.oqurystudio.karanel.android.network.NetworkRequestType
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormProgressViewModel @Inject constructor(
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

    var childId: String = ""
    var parentId: String = ""
    val progressPayload = FormProgress.Payload()

    private val _isFormProgressCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val isFormProgressCompleted: MutableLiveData<Boolean> = _isFormProgressCompleted


    fun updateFormProgressState() {
        if (progressPayload.record.weight == 0.0) {
            _isFormProgressCompleted.postValue(false)
            return
        }
        if (progressPayload.record.height == 0.0) {
            _isFormProgressCompleted.postValue(false)
            return
        }
        if (progressPayload.record.headCircumference == 0.0) {
            _isFormProgressCompleted.postValue(false)
            return
        }
        _isFormProgressCompleted.postValue(true)
        return
    }

    private val _response: MutableLiveData<FormProgress.Response> = MutableLiveData()
    val response: LiveData<FormProgress.Response> = _response

    fun submitProgress(token: String) {
        if (parentId.isBlank()) {
            requestAPI(_response, NetworkRequestType.FORM_PROGRESS) {
                repo.submitProgressAsParent(token, progressPayload)
            }
        } else {
            // TODO CHECK UPDATE
            requestAPI(_response, NetworkRequestType.FORM_PROGRESS) {
                repo.submitProgressAsPosyandu(token, progressPayload)
            }
        }
    }
}
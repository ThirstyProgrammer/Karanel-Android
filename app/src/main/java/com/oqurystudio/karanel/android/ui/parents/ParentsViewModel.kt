package com.oqurystudio.karanel.android.ui.parents

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oqurystudio.karanel.android.base.BaseViewModel
import com.oqurystudio.karanel.android.model.DashboardPosyandu
import com.oqurystudio.karanel.android.model.Parents
import com.oqurystudio.karanel.android.network.NetworkRequestType
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
import com.oqurystudio.karanel.android.util.defaultEmpty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParentsViewModel @Inject constructor(
    private var dataStore: DataStoreManager,
    private val repo: KaranelRepository
) : BaseViewModel() {

    companion object {
        const val ITEM_LIMIT = 12
    }

    private val _token: MutableLiveData<String> = MutableLiveData()
    val token: LiveData<String> = _token

    private var nextPage: Int = 0
    private var query: String = ""

    fun isNextUrlAvailable() = nextPage > 0

    fun updateNextPage(page: Int) {
        nextPage = page
    }

    fun updateQuery(query: String) {
        this.query = query
    }

    fun getToken() {
        viewModelScope.launch {
            dataStore.readValue(stringPreferencesKey(DataStoreManager.TOKEN), "") {
                _token.postValue(this)
            }
        }
    }

    private val _response: MutableLiveData<Parents.Response> = MutableLiveData()
    val response: LiveData<Parents.Response> = _response

    fun getParents(token: String = this.token.value.defaultEmpty(), page: Int = 1) {
        requestAPI(_response, NetworkRequestType.PARENTS) {
            repo.getParents(token, page, ITEM_LIMIT, query)
        }
    }

    fun loadMoreParents() {
        requestAPI(_response, NetworkRequestType.PARENTS_LOAD_MORE) {
            repo.getParents(token.value.defaultEmpty(), nextPage, ITEM_LIMIT, query)
        }
    }
}
package com.oqurystudio.karanel.android.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.oqurystudio.karanel.android.model.LoginParent
import com.oqurystudio.karanel.android.model.LoginPosyandu
import com.oqurystudio.karanel.android.model.UserType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        const val IS_LOGIN = "is_login"
        const val USER_TYPE = "user_type"
        const val TOKEN = "token"
        const val REFRESH_TOKEN = "refresh_token"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("karanel")

    private suspend fun <T> DataStore<Preferences>.getFromLocalStorage(
        PreferencesKey: Preferences.Key<T>, defaultValue: T, func: T.() -> Unit,
    ) {
        data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[PreferencesKey]
        }.collect {
            if (it == null) {
                func.invoke(defaultValue)
            } else {
                it.let { func.invoke(it as T) }
            }
        }
    }

    suspend fun <T> storeValue(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun storeUserPreference(data: LoginPosyandu.Data, userType: UserType) {
        context.dataStore.edit {
            it[booleanPreferencesKey(IS_LOGIN)] = true
            it[stringPreferencesKey(USER_TYPE)] = userType.value
            it[stringPreferencesKey(TOKEN)] = data.token.defaultEmpty()
            it[stringPreferencesKey(REFRESH_TOKEN)] = data.refreshToken.defaultEmpty()
        }
    }

    suspend fun storeUserPreference(data: LoginParent.Data, userType: UserType) {
        context.dataStore.edit {
            it[booleanPreferencesKey(IS_LOGIN)] = true
            it[stringPreferencesKey(USER_TYPE)] = userType.value
            it[stringPreferencesKey(TOKEN)] = data.token.defaultEmpty()
            it[stringPreferencesKey(REFRESH_TOKEN)] = data.refreshToken.defaultEmpty()
        }
    }

    suspend fun clearUserPreference() {
        context.dataStore.edit {
            it[booleanPreferencesKey(IS_LOGIN)] = false
            it[stringPreferencesKey(USER_TYPE)] = ""
            it[stringPreferencesKey(TOKEN)] = ""
            it[stringPreferencesKey(REFRESH_TOKEN)] = ""
        }
    }

    suspend fun <T> storeValue(keyList: HashMap<Preferences.Key<T>, T>,) {
        context.dataStore.edit {
            for (key in keyList.keys){
                it[key] = keyList.getValue(key)
            }
        }
    }

    suspend fun <T> readValue(key: Preferences.Key<T>, defaultValue: T, responseFunc: T.() -> Unit) {
        context.dataStore.getFromLocalStorage(key, defaultValue) {
            responseFunc.invoke(this)
        }
    }

    fun <T> readValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.dataStore.data.map {
            it[key] ?: defaultValue
        }
    }
}
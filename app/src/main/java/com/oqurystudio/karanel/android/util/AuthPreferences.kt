package com.oqurystudio.karanel.android.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val AUTH_KEY = stringPreferencesKey("auth_karanel")
    private val IS_LOGIN = booleanPreferencesKey("is_login_karanel")

    fun getAuth(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[AUTH_KEY].defaultEmpty()
        }
    }

    fun getIsLogin(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_LOGIN] ?: false
        }
    }

    suspend fun saveAuth(profile: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_KEY] = profile
            preferences[IS_LOGIN] = true
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
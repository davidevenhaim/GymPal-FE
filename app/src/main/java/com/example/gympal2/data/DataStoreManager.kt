package com.example.gympal2.data


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.gympal2.model.UserStorageData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

object DataStoreManager {
    private val TOKEN_KEY = stringPreferencesKey("u_token")
    private val ID_KEY = stringPreferencesKey("u_id")

    suspend fun saveToken(context: Context, data: UserStorageData) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = data.token
            preferences[ID_KEY] = data.id
        }
    }

    fun getToken(context: Context): Flow<UserStorageData?> {
        return context.dataStore.data.map { preferences ->
            UserStorageData(preferences[TOKEN_KEY] ?: "", preferences[ID_KEY] ?: "")
        }
    }

    suspend fun clearUserData(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(ID_KEY)
        }
    }
}
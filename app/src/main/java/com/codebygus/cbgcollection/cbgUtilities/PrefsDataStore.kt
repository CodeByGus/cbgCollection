package com.codebygus.cbgcollection.cbgUtilities

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codebygus.cbgcollection.GlobalApplication.Companion.appContext
import com.codebygus.cbgcollection.cbgUtilities.ConstantValues.sharedPrefs
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(
    name = sharedPrefs
)

object PrefsDataStore {

    suspend fun saveBooleanToSharedPrefs(keyName: String, keyValue: Boolean) {
        val booleanPrefKey = booleanPreferencesKey(keyName)
        appContext.dataStore.edit {preferences ->
            preferences[booleanPrefKey] = keyValue
        }
    }
    suspend fun saveIntToSharedPrefs(keyName: String, keyValue: Int) {
        val intPrefKey = intPreferencesKey(keyName)
        appContext.dataStore.edit {preferences ->
            preferences[intPrefKey] = keyValue
        }
    }
    suspend fun saveStringToSharedPrefs(keyName: String, keyValue: String) {
        val stringPrefKey = stringPreferencesKey(keyName)
        appContext.dataStore.edit {preferences ->
            preferences[stringPrefKey] = keyValue
        }
    }
    suspend fun readBooleanFromSharedPrefs(keyName: String): Boolean? {
        val booleanPrefKey = booleanPreferencesKey(keyName)
        val preferences = appContext.dataStore.data.first()
        return preferences[booleanPrefKey]
    }
    suspend fun readIntFromSharedPrefs(keyName: String): Int? {
        val intPrefKey = intPreferencesKey(keyName)
        val preferences = appContext.dataStore.data.first()
        return preferences[intPrefKey]
    }
    suspend fun readStringFromSharedPrefs(keyName: String): String? {
        val stringPrefKey = stringPreferencesKey(keyName)
        val preferences = appContext.dataStore.data.first()
        return preferences[stringPrefKey]
    }
    suspend fun removeBooleanFromSharedPrefs(keyName: String) {
        val booleanPrefKey = booleanPreferencesKey(keyName)
        appContext.dataStore.edit {preferences ->
            preferences.remove(booleanPrefKey)
        }
    }
    suspend fun removeIntFromSharedPrefs(keyName: String) {
        val intPrefKey = intPreferencesKey(keyName)
        appContext.dataStore.edit {preferences ->
            preferences.remove(intPrefKey)
        }
    }
    suspend fun removeStringFromSharedPrefs(keyName: String) {
        val stringPrefKey = stringPreferencesKey(keyName)
        appContext.dataStore.edit {preferences ->
            preferences.remove(stringPrefKey)
        }
    }
}
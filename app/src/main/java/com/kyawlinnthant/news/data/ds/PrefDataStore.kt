package com.kyawlinnthant.news.data.ds

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow

interface PrefDataStore {
    companion object {
        const val PREF_NAME = "news.ds.pref"
        val ENABLED_DYNAMIC_COLOR = booleanPreferencesKey("ds.enabled.dynamic")
        val APP_THEME = intPreferencesKey("ds.app.theme")
    }

    suspend fun putEnabledDynamicColor(status: Boolean)
    suspend fun pullEnabledDynamicColor(): Flow<Boolean?>
    suspend fun putAppTheme(value: Int)
    suspend fun pullAppTheme(): Flow<Int?>
    suspend fun clearDs()
}
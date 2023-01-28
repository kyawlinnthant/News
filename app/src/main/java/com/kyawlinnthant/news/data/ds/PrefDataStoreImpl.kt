package com.kyawlinnthant.news.data.ds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.kyawlinnthant.news.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class PrefDataStoreImpl @Inject constructor(
    private val ds: DataStore<Preferences>,
    @DispatcherModule.Io private val io: CoroutineDispatcher
) : PrefDataStore {

    override suspend fun putEnabledDynamicColor(status: Boolean) {
        withContext(io) {
            ds.edit {
                it[PrefDataStore.ENABLED_DYNAMIC_COLOR] = status
            }
        }
    }

    override suspend fun pullEnabledDynamicColor(): Flow<Boolean?> {
        return ds.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map {
            it[PrefDataStore.ENABLED_DYNAMIC_COLOR]
        }.flowOn(io)
    }

    override suspend fun putAppTheme(value: Int) {
        withContext(io) {
            ds.edit {
                it[PrefDataStore.APP_THEME] = value
            }
        }
    }

    override suspend fun pullAppTheme(): Flow<Int?> {
        return ds.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map {
            it[PrefDataStore.APP_THEME]
        }.flowOn(io)
    }

    override suspend fun clearDs() {
        withContext(io){
            ds.edit {
                it.clear()
            }
        }
    }
}
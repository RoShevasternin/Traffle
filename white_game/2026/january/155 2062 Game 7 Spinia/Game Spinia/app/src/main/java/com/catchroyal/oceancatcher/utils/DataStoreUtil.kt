package com.catchroyal.oceancatcher.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.catchroyal.oceancatcher.activityContext
import kotlinx.coroutines.flow.take

object DataStoreUtil {

    private val Context.jewelryDataStore: DataStore<Preferences> by preferencesDataStore(name = "jewelry")

    private val BEST_RESULT_KEY = intPreferencesKey("BEST_RESULT")



    suspend fun getBestResult(block: suspend (Int) -> Unit) = activityContext.jewelryDataStore.data.take(1).collect {
        block(it[BEST_RESULT_KEY] ?: 0)
    }

    suspend fun updateBestResult(block: suspend (Int) -> Int) = activityContext.jewelryDataStore.edit {
        it[BEST_RESULT_KEY] = block(it[BEST_RESULT_KEY] ?: 0)
    }
}
package com.relaxing.memorygame.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.relaxing.memorygame.util.AbstractDataStore

object DataStoreManager: AbstractDataStore() {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "PREFERENCES")



    object Key: AbstractDataStore.DataStoreElement<String>() {
        override val key = stringPreferencesKey("Norvegia")
    }

    object Link: AbstractDataStore.DataStoreElement<String>() {
        override val key = stringPreferencesKey("ismadagaskar")
    }

}


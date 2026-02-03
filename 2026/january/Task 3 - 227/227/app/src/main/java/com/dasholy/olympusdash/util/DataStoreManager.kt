package com.dasholy.olympusdash.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object DataStoreManager: AbstractDataStore() {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DataStore_key_link")



    object Key: AbstractDataStore.DataStoreElement<String>() {
        override val key = stringPreferencesKey("Key")
    }

    object Link: AbstractDataStore.DataStoreElement<String>() {
        override val key = stringPreferencesKey("Link")
    }

}


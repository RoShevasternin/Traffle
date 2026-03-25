package com.zentorix.nv.casino.game.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zentorix.nv.casino.util.AbstractDataStore

object GameDataStoreManager: AbstractDataStore() {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "GAME_DATA_STORE")



    object Balance: DataStoreElement<Long>() {
        override val key = longPreferencesKey("balance_key")
    }

    object Date: DataStoreElement<String>() {
        override val key = stringPreferencesKey("date")
    }

    object FileExtension: DataStoreElement<String>() {
        override val key = stringPreferencesKey("fileExtension_key")
    }

    object NickName: DataStoreElement<String>() {
        override val key = stringPreferencesKey("nickname_key")
    }

}


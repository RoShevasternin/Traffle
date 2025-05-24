package com.viade.bepuzzle.game.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.viade.bepuzzle.appContext
import kotlinx.coroutines.flow.first

object GameDataStoreManager: AbstractDataStore() {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "GAME_DATA_STORE")


    object Balance: AbstractDataStore.DataStoreElement<Int>() {
        override val key = intPreferencesKey("balance")
    }
    object LevelOpened: AbstractDataStore.DataStoreElement<String>() {
        override val key = stringPreferencesKey("level_opened")
    }
    object K1: AbstractDataStore.DataStoreElement<Int>() {
        override val key = intPreferencesKey("k1")
    }
    object K2: AbstractDataStore.DataStoreElement<Int>() {
        override val key = intPreferencesKey("k2")
    }
    object K5: AbstractDataStore.DataStoreElement<Int>() {
        override val key = intPreferencesKey("k5")
    }
    object GalleryStars: AbstractDataStore.DataStoreElement<String>() {
        override val key = stringPreferencesKey("gallery_stars")
    }
    object Awords: AbstractDataStore.DataStoreElement<String>() {
        override val key = stringPreferencesKey("awords")
    }
}

abstract class AbstractDataStore {
    abstract val Context.dataStore: DataStore<Preferences>



    abstract inner class DataStoreElement<T> {
        abstract val key: Preferences.Key<T>

        open suspend fun collect(block: suspend (T?) -> Unit) {
            appContext.dataStore.data.collect { block(it[key]) }
        }

        open suspend fun update(block: suspend (T?) -> T) {
            appContext.dataStore.edit { it[key] = block(it[key]) }
        }

        open suspend fun get(): T? {
            return appContext.dataStore.data.first()[key]
        }
    }
}


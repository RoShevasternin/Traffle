package com.gorillaz.puzzlegame.game.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gorillaz.puzzlegame.appContext
import kotlinx.coroutines.flow.first

object DataStoreManager: AbstractDataStore() {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DATA_STORE")

    // Int
    object Gems: DataStoreElement<Int>() {
        override val key = intPreferencesKey("gems")
    }
    object Gold: DataStoreElement<Int>() {
        override val key = intPreferencesKey("gold")
    }
    object Level: DataStoreElement<Int>() {
        override val key = intPreferencesKey("level")
    }

    // String
//    object Nickname: DataStoreElement<String>() {
//        override val key = stringPreferencesKey("nickname")
//    }

    // Data
    object User: DataStoreElement<String>() {
        override val key = stringPreferencesKey("user")
    }
    object Puzzle: DataStoreElement<String>() {
        override val key = stringPreferencesKey("puzzle")
    }
    object LevelJackpot: DataStoreElement<String>() {
        override val key = stringPreferencesKey("level_jackpot")
    }
    object Achievement: DataStoreElement<String>() {
        override val key = stringPreferencesKey("achievement")
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


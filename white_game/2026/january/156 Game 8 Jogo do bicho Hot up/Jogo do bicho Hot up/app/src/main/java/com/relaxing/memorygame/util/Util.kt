package com.relaxing.memorygame.util

import android.app.Activity
import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import com.relaxing.memorygame.appContext

fun log(message: String) {
//    Log.i("VLAD", message)
}

fun cancelCoroutinesAll(vararg coroutine: CoroutineScope?) {
    coroutine.forEach { it?.cancel() }
}

fun View.setVisible(visibility: Int) {
    if (this.visibility != visibility) this.visibility = visibility
}

fun Activity.internetConnection(): Boolean {
    var haveConnectedWifi   = false
    var haveConnectedMobile = false
    (getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager).allNetworkInfo.onEach { networkItem ->
        if (networkItem.typeName.equals(
                "WIFI",
                ignoreCase = true
            )
        ) if (networkItem.isConnected) haveConnectedWifi = true
        if (networkItem.typeName.equals(
                "MOBILE",
                ignoreCase = true
            )
        ) if (networkItem.isConnected) haveConnectedMobile = true
    }

    return haveConnectedWifi || haveConnectedMobile
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

fun Context.isUSB() = Settings.Secure.getInt(contentResolver, Settings.Secure.ADB_ENABLED, 0) == 1

fun Context.getBatteryPercentage(): Int {
    val bm = getSystemService(BATTERY_SERVICE) as BatteryManager
    return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
}

fun Context.isBatteryCharging(): Boolean {
    val bm = getSystemService(BATTERY_SERVICE) as BatteryManager
    return bm.isCharging
}
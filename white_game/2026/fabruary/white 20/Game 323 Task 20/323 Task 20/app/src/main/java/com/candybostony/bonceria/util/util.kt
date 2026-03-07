package com.candybostony.bonceria.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

val Any.currentClassName: String get() = this::class.java.simpleName

fun log(message: String) {
    Log.i("YRAaaaaaa", message)
}

fun cancelCoroutinesAll(vararg coroutine: CoroutineScope?) {
    coroutine.forEach { it?.cancel() }
}
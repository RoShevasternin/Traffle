package com.pixe.lkicko.perlin.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import java.util.*

fun log(message: String) {
    Log.i("TorMer", message)
}

fun cancelCoroutinesAll(vararg coroutine: CoroutineScope?) {
    coroutine.forEach { it?.cancel() }
}

fun getTodayDay(): Int {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.DAY_OF_MONTH)
}
package com.sugaraxplosion.candysmoy

import android.app.Application
import android.content.Context
import com.sugaraxplosion.candysmoy.util.log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

lateinit var appContext: Context private set

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
    }

}
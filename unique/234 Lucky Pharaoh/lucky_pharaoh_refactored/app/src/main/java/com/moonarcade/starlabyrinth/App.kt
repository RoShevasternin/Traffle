/*
 * Refactored Application Module
 * Build: 6B5501D4
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth

import android.app.Application
import android.content.Context

lateinit var appContext: Context private set

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}
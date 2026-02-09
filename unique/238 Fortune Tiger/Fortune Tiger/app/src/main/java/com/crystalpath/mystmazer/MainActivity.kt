package com.crystalpath.mystmazer

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.crystalpath.mystmazer.databinding.ActivityMainBinding
import com.crystalpath.mystmazer.util.OneTime
import com.crystalpath.mystmazer.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess
import androidx.core.net.toUri

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    companion object {
        var statusBarHeight = 0
    }

    private val coroutine  = CoroutineScope(Dispatchers.Default)
    private val onceExit   = OneTime()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate")

        enableEdgeToEdge()
        hideStatusBar()
        hideNavigationBar()

        initialize()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            insets
        }
    }

    override fun exit() {
        onceExit.use {
            log("exit")
            coroutine.launch(Dispatchers.Main) {
                finishAndRemoveTask()
                delay(100)
                exitProcess(0)
            }
        }
    }

    private fun initialize() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Logic -----------------------------------------------------------------------------------------

    private fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // API 30 і вище
            window.insetsController?.let {
                it.hide(WindowInsets.Type.navigationBars()) // Ховаємо панель навігації
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Для API нижче 30
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // API 30 і вище
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars()) // Ховаємо панель навігації
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Для API нижче 30
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }


    fun shareGame() {
        val packageName = applicationContext.packageName // Отримати поточний пакет додатка
        val playStoreLink = "https://play.google.com/store/apps/details?id=$packageName"

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Try this game!")
            putExtra(Intent.EXTRA_TEXT, "Hi! I'm playing this cool game. Download it here: $playStoreLink")
        }

        startActivity(Intent.createChooser(intent, "Share via"))
    }

    fun openPlayStoreForRating() {
        val packageName = applicationContext.packageName // Отримуємо поточний пакет гри
        try {
            // Відкриваємо додаток Google Play, якщо встановлений
            val intent = Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri())
            intent.setPackage("com.android.vending") // Явно вказуємо Google Play
            startActivity(intent)
        } catch (e: Exception) {
            // Якщо Play Маркету немає, відкриваємо сторінку в браузері
            val intent = Intent(Intent.ACTION_VIEW, "https://play.google.com/store/apps/details?id=$packageName".toUri())
            startActivity(intent)
        }
    }

}
package com.bigfish.pairtoper

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.bigfish.pairtoper.databinding.ActivityMainBinding
import com.bigfish.pairtoper.game.utils.gdxGame
import com.bigfish.pairtoper.util.OneTime
import com.bigfish.pairtoper.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    companion object {
        var statusBarHeight = 0
        var navBarHeight    = 0
    }

    private val onceExit            = OneTime()
    private val onceSystemBarHeight = OneTime()

    lateinit var binding      : ActivityMainBinding

    val coroutine = CoroutineScope(Dispatchers.Main)

    val windowInsetsController by lazy { WindowCompat.getInsetsController(window, window.decorView) }

    /*val mapBlockKeyboardHeight = mutableMapOf<String, (Boolean, Int) -> Unit>()*/

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initialize()

        //startKeyboardHeightListener()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            onceSystemBarHeight.use {
                statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                navBarHeight    = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

                // hide Status or Nav bar (після встановлення їх розмірів)
                windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
                windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

            WindowInsetsCompat.CONSUMED
        }
    }

    override fun exit() {
        onceExit.use {
            log("exit")
            coroutine.launch {
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

}
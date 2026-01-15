package com.royaltombsecrets.miniquizler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.royaltombsecrets.miniquizler.databinding.ActivityGameBinding
import com.royaltombsecrets.miniquizler.util.OneTime
import com.royaltombsecrets.miniquizler.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val onceExit = OneTime()


    override fun exit() {
        onceExit.use {
            log("exit")
            coroutine.launch(Dispatchers.Main) {
                finishAndRemoveTask()
                finishAffinity()
                delay(100)
                exitProcess(0)
            }
        }
    }

    private fun initialize() {
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    private val coroutine = CoroutineScope(Dispatchers.Default)

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

}
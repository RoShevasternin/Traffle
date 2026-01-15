package com.flightcoll.bridgertons

import android.os.Bundle
import android.webkit.*
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.flightcoll.bridgertons.databinding.ActivityMainBinding
import com.flightcoll.bridgertons.util.OneTime
import com.flightcoll.bridgertons.util.log
import kotlinx.coroutines.*
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)

    private val onceExit  = OneTime()

    private lateinit var wersos: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun factorial(n: Int): Int {
        return if (n <= 1) {
            1
        } else {
            n * factorial(n - 1)
        }
    }

    private fun countDigits(number: Int): Int {
        return if (number == 0) {
            0
        } else {
            1 + countDigits(number / 10)
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
        wersos = ActivityMainBinding.inflate(layoutInflater)
        setContentView(wersos.root)
        navController = findNavController(R.id.nav_host_fragment)

        setStartDestination(R.id.libGDXFragment)
    }




    private fun setStartDestination(@IdRes destinationId: Int) {
        navController.run { navInflater.inflate(R.navigation.nav_graph).apply { setStartDestination(destinationId) }.also { setGraph(it, null) } }
    }

}
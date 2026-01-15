package com.catchroyal.oceancatcher

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.catchroyal.oceancatcher.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

lateinit var activityContext: Activity private set
lateinit var navController: NavController private set
lateinit var binding: ActivityMainBinding private set

class AndroidLauncher : FragmentActivity(), AndroidFragmentApplication.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityContext = this
        navController = findNavController(R.id.nav_host_fragment)

    }

    override fun exit() {
        CoroutineScope(Dispatchers.Main).launch {
            finishAndRemoveTask()
            delay(200)
            exitProcess(0)
        }
    }

}
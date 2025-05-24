package com.slotscity.official

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.slotscity.official.databinding.ActivityMainBinding
import com.slotscity.official.util.Lottie
import com.slotscity.official.util.Once
import com.slotscity.official.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = Once()

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController

    lateinit var lottie: Lottie

    private var blockImageFromGalleryResult: (Uri?) -> Unit = {}
    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? -> blockImageFromGalleryResult(uri) }

    private var blockPermissionLauncher: (Boolean) -> Unit = {}
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean -> blockPermissionLauncher(isGranted) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()

        lottie.showLoader()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setStartDestination(R.id.libGDXFragment)
    }


    override fun exit() {
        onceExit.once {
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
        navController = findNavController(R.id.nav_host_fragment)
        lottie        = Lottie(binding)


    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun setStartDestination(@IdRes destinationId: Int) {
        navController.run { navInflater.inflate(R.navigation.nav_graph).apply { setStartDestination(destinationId) }.also { setGraph(it, null) } }
    }

    fun selectImageFromGallery(block: (Uri?) -> Unit) {
        blockImageFromGalleryResult = block
        selectImageFromGalleryResult.launch("image/*")
    }

    fun requestPermission(permission: String, block: (Boolean) -> Unit) {
        blockPermissionLauncher = block
        requestPermissionLauncher.launch(permission)
    }


}
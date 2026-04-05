package com.bramlix.cas888.casino

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.bramlix.cas888.casino.databinding.ActivityMainBinding
import com.bramlix.cas888.casino.util.Once
import com.bramlix.cas888.casino.util.log
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

    private val onceExit            = Once()
    private val onceSystemBarHeight = Once()

    lateinit var binding      : ActivityMainBinding

    private var blockImageFromGalleryResult: (Uri?) -> Unit = {}
    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? -> blockImageFromGalleryResult(uri) }

    private var blockPermissionLauncher: (Boolean) -> Unit = {}
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean -> blockPermissionLauncher(isGranted) }


    // У MainActivity.kt
    private var blockCameraResult: (Bitmap?) -> Unit = {}
    private val takePictureResult = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        blockCameraResult(bitmap)
    }

    fun takePhoto(block: (Bitmap?) -> Unit) {
        blockCameraResult = block
        // Тут запитуємо дозвіл саме на КАМЕРУ
        requestPermission(Manifest.permission.CAMERA) { isGranted ->
            if (isGranted) takePictureResult.launch(null)
        }
    }

    val coroutine = CoroutineScope(Dispatchers.Main)

    val windowInsetsController by lazy { WindowCompat.getInsetsController(window, window.decorView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initialize()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            onceSystemBarHeight.once {
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
        onceExit.once {
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


    fun selectImageFromGallery(block: (Uri?) -> Unit) {
        blockImageFromGalleryResult = block
        selectImageFromGalleryResult.launch("image/*")
    }

    fun requestPermission(permission: String, block: (Boolean) -> Unit) {
        blockPermissionLauncher = block
        requestPermissionLauncher.launch(permission)
    }

}
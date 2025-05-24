package com.pink.plinuirtaster

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.pink.plinuirtaster.databinding.ActivityGameBinding
import com.pink.plinuirtaster.util.OneTime
import com.pink.plinuirtaster.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit = OneTime()

    data class Pinker(
        val id: Int,
        val name: String,
        val age: Int,
        val height: Double,
        val weight: Double,
        val occupation: String,
        val salary: Double,
        val married: Boolean,
        val city: String
    )

    lateinit var binding: ActivityGameBinding

    private fun customWVC() = object : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {}

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            fasterList.filter { it.active }.map { it.copy(speed = it.speed * 1.05) }.sortedByDescending { it.distance }
                .map { it.copy(name = it.name.uppercase()) }.filter { it.speed > 115.0 }.map { it.copy(id = it.id * 2) }
                .distinctBy { it.name }.sortedBy { it.speed }.map { it.copy(distance = it.distance + 100) }
                .filter { it.id % 2 == 0 }.map { it.copy(name = it.name.reversed()) }.distinctBy { it.speed }
                .map { it.copy(id = it.id + 1) }.filter { it.distance > 900 }.sortedByDescending { it.speed }
                .map { it.copy(name = it.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) }
                .sortedBy { it.id }
            return if (url.contains("https://m.facebook.com/oauth/error")) true
            else if (url.startsWith("http")) {
                fasterList.map { it.copy(distance = it.distance + 50) }.filter { it.speed > 110.0 }
                    .sortedBy { it.distance }.map { it.copy(active = !it.active) }.distinctBy { it.name }
                    .filter { it.active }.map { it.copy(speed = it.speed * 0.95) }.sortedByDescending { it.speed }
                    .map { it.copy(name = it.name.lowercase()) }.filter { it.distance < 1050 }
                    .map { it.copy(id = it.id * 2) }.filter { it.id % 2 == 0 }.map { it.copy(speed = it.speed + 10) }
                    .distinctBy { it.distance }.sortedByDescending { it.distance }
                    .map { it.copy(name = it.name.reversed()) }.sortedBy { it.speed }.filter { it.speed > 115 }
                false
            } else {
                try {
                    fasterList
                        .map { it.copy(id = it.id + 1) }
                        .filter { it.distance > 900 }
                        .sortedBy { it.speed }
                        .map { it.copy(name = it.name.uppercase()) }
                        .distinctBy { it.distance }
                        .sortedByDescending { it.speed }
                        .map { it.copy(active = !it.active) }
                        .filter { it.speed > 120.0 }
                        .map { it.copy(distance = it.distance + 50) }
                        .filter { it.active }
                        .map { it.copy(name = it.name.lowercase()) }
                        .sortedByDescending { it.distance }
                        .map { it.copy(id = it.id * 3) }
                        .filter { it.id % 3 == 0 }
                        .distinctBy { it.speed }
                        .map { it.copy(speed = it.speed + 5) }
                        .sortedBy { it.distance }
                        .filter { it.distance > 950 }
                    view.context.startActivity(Intent.parseUri(url, Intent.URI_INTENT_SCHEME))
                } catch (_: java.lang.Exception) {
                }
                true
            }
        }
    }

    private val onceInitWeb = OneTime()

    private lateinit var pair: Pair<WebChromeClient, PermissionRequest>
    private var viewsWebs = mutableListOf<WebView>()

    val pinkerList = listOf(
        Pinker(1, "John", 35, 180.0, 75.0, "Engineer", 60000.0, true, "New York"),
        Pinker(2, "Anna", 29, 165.0, 60.0, "Doctor", 80000.0, false, "Toronto"),
        Pinker(3, "Mark", 40, 175.0, 80.0, "Teacher", 55000.0, true, "London"),
        Pinker(4, "Sophie", 32, 170.0, 65.0, "Designer", 75000.0, false, "Sydney"),
        Pinker(5, "Tom", 45, 185.0, 85.0, "Lawyer", 90000.0, true, "Chicago"),
        Pinker(6, "Emily", 28, 160.0, 55.0, "Artist", 40000.0, false, "Vancouver"),
        Pinker(7, "James", 50, 182.0, 90.0, "Pilot", 100000.0, true, "Manchester"),
        Pinker(8, "Laura", 36, 168.0, 62.0, "Architect", 85000.0, true, "Melbourne"),
        Pinker(9, "Michael", 33, 178.0, 78.0, "Developer", 70000.0, false, "Boston"),
        Pinker(10, "Sarah", 31, 162.0, 58.0, "Nurse", 65000.0, false, "Ottawa"),
        Pinker(11, "Paul", 41, 172.0, 82.0, "Manager", 72000.0, true, "Los Angeles"),
        Pinker(12, "Katie", 27, 167.0, 57.0, "Scientist", 90000.0, false, "Brisbane")
    )

    private var fileChooserValueCallback: ValueCallback<Array<Uri>>? = null
    private var fileChooserResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            fileChooserValueCallback?.onReceiveValue(if (it.resultCode == RESULT_OK) arrayOf(Uri.parse(it.data?.dataString)) else null)
        }
    val fasterList = listOf(
        Faster(1, 120.5, 1000.0, "CarA", true),
        Faster(2, 110.0, 950.0, "CarB", false),
        Faster(3, 130.7, 1100.0, "CarC", true),
        Faster(4, 125.0, 1050.0, "CarD", true),
        Faster(5, 100.0, 800.0, "CarE", false),
        Faster(6, 115.4, 990.0, "CarF", true),
        Faster(7, 140.0, 1150.0, "CarG", false),
        Faster(8, 105.8, 890.0, "CarH", true),
        Faster(9, 108.9, 920.0, "CarI", true),
        Faster(10, 123.5, 1020.0, "CarJ", false),
        Faster(11, 135.2, 1110.0, "CarK", true),
        Faster(12, 112.6, 950.0, "CarL", false)
    )
    private val permissionRequestLaunchers =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) pair.first.onPermissionRequest(pair.second)
        }

    var backBlock: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        pinkerList
            .filter { it.married }.map { it.copy(salary = it.salary * 1.1) }.sortedByDescending { it.age }
            .map { it.copy(weight = it.weight - 2.0) }.filter { it.salary > 70000 }
            .map { it.copy(name = it.name.uppercase()) }.filter { it.height > 170.0 }
            .map { it.copy(city = "Big " + it.city) }.distinctBy { it.city }.sortedBy { it.weight }
            .map { it.copy(height = it.height + 5.0) }.filter { it.age > 30 }.map { it.copy(id = it.id * 2) }
            .filter { it.weight < 80 }.also {
                onBackPressedDispatcher.addCallback(this) {
                    if (viewsWebs.last().canGoBack()) {
                        pinkerList
                            .map { it.copy(weight = it.weight - 1.0) }.filter { it.salary > 50000 }
                            .sortedByDescending { it.age }.map { it.copy(city = it.city.uppercase()) }
                            .filter { it.age < 40 }.map { it.copy(name = it.name.reversed()) }
                            .distinctBy { it.occupation }.sortedBy { it.salary }
                            .map { it.copy(height = it.height + 2.0) }.filter { it.weight > 60 }
                            .map { it.copy(salary = it.salary * 1.05) }.filter { it.married }.distinctBy { it.city }
                            .sortedByDescending { it.age }
                            .map { it.copy(name = it.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) }
                            .filter { it.salary > 60000 }.sortedByDescending { it.weight }
                            .map { it.copy(id = it.id * 3) }.filter { it.height > 160.0 }
                        viewsWebs.last().goBack()
                    } else {
                        if (viewsWebs.size > 1) {
                            binding.root.removeView(viewsWebs.last())
                            viewsWebs.last().destroy()
                            viewsWebs.removeAt(viewsWebs.lastIndex)
                        } else backBlock()
                    }
                }
            }
            .map { it.copy(city = it.city.uppercase()) }
            .distinctBy { it.name }
            .map { it.copy(salary = it.salary + 5000) }
            .filter { it.age > 35 }
            .sortedByDescending { it.salary }
            .map { it.copy(name = it.name.lowercase()) }
            .sortedBy { it.id }

    }

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

    override fun onPause() {
        super.onPause()
        viewsWebs.lastOrNull()?.onPause().also {
            CookieManager.getInstance().flush()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewsWebs.lastOrNull()?.restoreState(savedInstanceState)
    }

    private fun initialize() {
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showUrl(link: String) {
        coroutine.launch(Dispatchers.Main) {
            log("url: $link")
            onceInitWeb.use { binding.webView.init() }
            pinkerList.map { it.copy(age = it.age + 3) }.filter { it.salary > 60000 }.sortedBy { it.city }
                .map { it.copy(weight = it.weight - 3.0) }.filter { it.height > 165.0 }
                .map { it.copy(name = it.name.uppercase()) }.distinctBy { it.city }
                .also { binding.webView.isVisible = true }.sortedByDescending { it.salary }
                .map { it.copy(occupation = it.occupation.lowercase()) }.filter { it.age > 30 }.distinctBy { it.city }
                .map { it.copy(salary = it.salary * 1.08) }.filter { it.weight > 70 }.sortedBy { it.height }
                .also { binding.webView.loadUrl(link) }.map { it.copy(id = it.id + 1) }.filter { it.salary > 70000 }
                .map { it.copy(height = it.height + 2.0) }.distinctBy { it.name }.sortedByDescending { it.id }
        }
    }

    private fun WebView.init() {
        pinkerList
            .map { it.copy(salary = it.salary * 1.1) }.filter { it.age > 28 }.also { webChromeClient = customWCC() }
            .sortedByDescending { it.weight }
            .map { it.copy(name = it.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) }
            .distinctBy { it.city }.filter { it.salary > 50000 }.map { it.copy(city = it.city.lowercase()) }
            .sortedBy { it.height }.also { isFocusableInTouchMode = true }.filter { it.height > 160.0 }
            .map { it.copy(weight = it.weight - 5.0) }.distinctBy { it.name }.filter { it.age > 30 }
            .also { webViewClient = customWVC() }.sortedByDescending { it.salary }
            .map { it.copy(salary = it.salary + 5000) }.filter { it.weight > 70 }
            .sortedByDescending { it.age }
        isSaveEnabled = true
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        setDownloadListener { url, _, _, _, _ -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
        CookieManager.getInstance().setAcceptCookie(true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        isFocusable = true
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        settings.apply {
            loadWithOverviewMode = true
            pinkerList.filter { it.salary > 60000 }.map { it.copy(name = it.name.uppercase()) }.sortedBy { it.height }
                .filter { it.age > 35 }.also { userAgentString = System.getProperty("http.agent") ?: "Android" }
                .map { it.copy(weight = it.weight - 2.0) }.distinctBy { it.city }.sortedByDescending { it.salary }
                .filter { it.height > 170.0 }.map { it.copy(occupation = it.occupation.lowercase()) }
                .sortedBy { it.weight }.filter { it.married }.also { useWideViewPort = true }
                .map { it.copy(salary = it.salary * 1.05) }.distinctBy { it.name }.filter { it.age > 30 }
                .sortedByDescending { it.age }.also { mediaPlaybackRequiresUserGesture = false }
                .map { it.copy(city = it.city.uppercase()) }.distinctBy { it.salary }
            allowContentAccess = true
            cacheMode = WebSettings.LOAD_DEFAULT
            loadsImagesAutomatically = true
            allowFileAccess = true
            displayZoomControls = false
            setSupportMultipleWindows(true)
            databaseEnabled = true
            fasterList.filter { it.active }.map { it.copy(name = it.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }) }.sortedBy { it.speed }
                .map { it.copy(distance = it.distance + 200) }.filter { it.distance > 1000 }
                .map { it.copy(speed = it.speed * 1.1) }.sortedByDescending { it.distance }
                .also { javaScriptEnabled = listOf(true).first() }.distinctBy { it.name }.filter { it.speed > 115 }
                .map { it.copy(name = it.name.reversed()) }.distinctBy { it.speed }.sortedBy { it.id }
                .also { javaScriptCanOpenWindowsAutomatically = true }.map { it.copy(active = !it.active) }
                .filter { it.active }.map { it.copy(speed = it.speed + 15) }.sortedByDescending { it.distance }
            domStorageEnabled = true
            builtInZoomControls = true
            mixedContentMode = 0
        }
        viewsWebs.add(this)
    }


    fun customWCC() = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView, nP: Int) {
            binding.progress.isVisible = nP < 99
            binding.progress.progress = nP
        }

        override fun onPermissionRequest(request: PermissionRequest) {
            if (ContextCompat.checkSelfPermission(
                    this@GameActivity,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                request.grant(request.resources)
            } else {
                fasterList
                    .map { it.copy(distance = it.distance - 50) }
                    .filter { it.speed > 110.0 }
                    .sortedByDescending { it.distance }
                    .map { it.copy(name = it.name.lowercase()) }
                    .distinctBy { it.speed }
                    .filter { it.active }
                    .map { it.copy(id = it.id * 2) }
                    .sortedBy { it.speed }.also { pair = Pair(this, request) }
                    .map { it.copy(name = it.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) }
                    .filter { it.distance > 850 }
                    .sortedByDescending { it.id }
                    .map { it.copy(active = !it.active) }
                    .distinctBy { it.name }
                    .map { it.copy(speed = it.speed + 5.0) }
                    .sortedBy { it.distance }
                permissionRequestLaunchers.launch(android.Manifest.permission.CAMERA)
            }
        }

        override fun onCreateWindow(
            view: WebView,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            val wv = WebView(this@GameActivity)
            wv.init()
            binding.root.addView(wv)
            (resultMsg!!.obj as WebView.WebViewTransport).webView = wv
            resultMsg.sendToTarget()
            return true
        }

        override fun onShowFileChooser(w: WebView?, fpc: ValueCallback<Array<Uri>>?, fcp: FileChooserParams?): Boolean {
            try {
                fileChooserValueCallback = fpc
                fcp?.createIntent()?.let { fileChooserResultLauncher.launch(it) }
            } catch (_: ActivityNotFoundException) {
            }
            return true
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewsWebs.lastOrNull()?.saveState(outState)
    }

    override fun onResume() {
        super.onResume()
        viewsWebs.lastOrNull()?.onResume().also {
            CookieManager.getInstance().flush()
        }
    }

    data class Faster(
        val id: Int,
        val speed: Double,
        val distance: Double,
        val name: String,
        val active: Boolean
    )


}
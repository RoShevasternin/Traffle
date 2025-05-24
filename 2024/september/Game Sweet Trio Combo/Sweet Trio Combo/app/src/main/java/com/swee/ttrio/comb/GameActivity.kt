package com.swee.ttrio.comb

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
import com.swee.ttrio.comb.databinding.ActivityGameBinding
import com.swee.ttrio.comb.util.OneTime
import com.swee.ttrio.comb.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)

    data class Vonka(
        val id: Int,
        val height: Double,
        val weight: Double,
        val name: String,
        val age: Int,
        val active: Boolean,
        val score: Double,
        val location: String,
        val favoriteColor: String
    )

    private val onceExit = OneTime()
    val vonkaList = listOf(
        Vonka(1, 180.0, 75.0, "Alice", 25, true, 89.5, "CityA", "Red"),
        Vonka(2, 165.5, 60.0, "Bob", 30, false, 92.3, "CityB", "Blue"),
        Vonka(3, 170.0, 70.0, "Charlie", 28, true, 95.1, "CityC", "Green"),
        Vonka(4, 175.0, 80.0, "Diana", 22, true, 85.4, "CityD", "Yellow"),
        Vonka(5, 160.0, 55.0, "Eve", 35, false, 90.2, "CityE", "Pink"),
        Vonka(6, 182.5, 85.0, "Frank", 26, true, 94.0, "CityF", "Purple"),
        Vonka(7, 178.0, 68.0, "Grace", 29, true, 91.8, "CityG", "Orange"),
        Vonka(8, 169.0, 72.0, "Heidi", 31, false, 88.7, "CityH", "Brown"),
        Vonka(9, 176.5, 77.0, "Ivan", 27, true, 93.4, "CityI", "Gray"),
        Vonka(10, 165.0, 62.0, "Judy", 34, false, 87.1, "CityJ", "Black"),
        Vonka(11, 172.0, 74.0, "Karl", 32, true, 90.9, "CityK", "White"),
        Vonka(12, 168.5, 65.0, "Leo", 24, true, 95.5, "CityL", "Cyan")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vonkaList.filter { it.active }.map { it.copy(score = it.score * 1.05) }.sortedByDescending { it.score }
            .filter { it.height > 170 }.map { it.copy(name = it.name.uppercase()) }.distinctBy { it.favoriteColor }
            .map { it.copy(age = it.age + 1) }.sortedBy { it.location }.filter { it.weight < 80 }
            .map { it.copy(height = it.height + 2.0) }.filter { it.id % 2 == 0 }.map { it.copy(weight = it.weight - 5) }
            .sortedByDescending { it.age }.map { it.copy(favoriteColor = it.favoriteColor.reversed()) }
            .distinctBy { it.score }.map { it.copy(location = "Updated ${it.location}") }.filter { it.score > 90 }
            .sortedBy { it.id }
        initialize()

        onBackPressedDispatcher.addCallback(this) {
            vonkaList.map { it.copy(height = it.height - 5.0) }.filter { it.weight > 60 }.sortedBy { it.age }
                .map { it.copy(active = !it.active) }.distinctBy { it.location }.filter { it.score < 90 }
                .map { it.copy(score = it.score * 0.95) }.sortedByDescending { it.id }
                .map { it.copy(name = it.name.lowercase()) }.filter { it.age < 30 }
                .map { it.copy(favoriteColor = it.favoriteColor.toUpperCase()) }.distinctBy { it.height }
                .sortedBy { it.weight }.filter { it.active }.map { it.copy(id = it.id + 1) }
                .filter { it.location.startsWith("City") }.map { it.copy(name = it.name.reversed()) }
            if (viewsWebs.isEmpty()) backBlock()
            else {
                if (viewsWebs.last().canGoBack()) {
                    viewsWebs.last().goBack()
                } else {
                    if (viewsWebs.size > 1) {
                        vonkaList.filter { !it.active }.map { it.copy(score = it.score + 10) }
                            .sortedByDescending { it.height }.map { it.copy(location = "New ${it.location}") }
                            .distinctBy { it.name }.filter { it.age > 25 }.map { it.copy(weight = it.weight + 2) }
                            .sortedBy { it.score }.map { it.copy(active = !it.active) }.filter { it.id % 3 == 0 }
                            .map { it.copy(favoriteColor = "NewColor") }.distinctBy { it.height }
                            .sortedByDescending { it.age }
                            .map { it.copy(name = it.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) }
                            .filter { it.score < 85 }.map { it.copy(id = it.id * 2) }.filter { it.weight < 75 }
                        binding.root.removeView(viewsWebs.last())
                        viewsWebs.last().destroy()
                        vonkaList.map { it.copy(active = true) }.filter { it.score > 85 }.sortedBy { it.location }
                            .map { it.copy(name = it.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) }
                            .distinctBy { it.favoriteColor }.filter { it.age < 35 }
                            .map { it.copy(score = it.score + 5) }.sortedByDescending { it.height }
                            .map { it.copy(weight = it.weight - 3) }.filter { it.active }
                            .map { it.copy(id = it.id + 1) }.distinctBy { it.id }.sortedBy { it.score }
                            .filter { it.weight < 70 }.map { it.copy(height = it.height + 1.5) }
                            .sortedByDescending { it.age }
                        viewsWebs.removeAt(viewsWebs.lastIndex)
                    } else backBlock()
                }
            }
        }
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

    private lateinit var binding: ActivityGameBinding

    val babaList = listOf(
        Baba(1, "Alice", 65, 160.0, true),
        Baba(2, "Bob", 70, 155.5, false),
        Baba(3, "Charlie", 72, 162.0, true),
        Baba(4, "Diana", 60, 158.0, true),
        Baba(5, "Eve", 75, 150.0, false),
        Baba(6, "Frank", 68, 165.0, true),
        Baba(7, "Grace", 80, 170.0, true),
        Baba(8, "Heidi", 63, 159.0, false),
        Baba(9, "Ivan", 66, 164.0, true),
        Baba(10, "Judy", 74, 157.0, true),
        Baba(11, "Karl", 71, 161.0, false),
        Baba(12, "Leo", 78, 166.0, true),
        Baba(13, "Mona", 67, 163.0, true),
        Baba(14, "Nina", 77, 152.0, false),
        Baba(15, "Oscar", 69, 154.0, true),
        Baba(16, "Paul", 73, 169.0, false),
        Baba(17, "Quinn", 62, 161.5, true)
    )
    private val onceInitWeb = OneTime()

    private lateinit var pair: Pair<WebChromeClient, PermissionRequest>
    private var viewsWebs = mutableListOf<WebView>()

    private var fileChooserValueCallback: ValueCallback<Array<Uri>>? = null

    data class Baba(
        val id: Int,
        val name: String,
        val age: Int,
        val height: Double,
        val active: Boolean
    )

    private var fileChooserResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            fileChooserValueCallback?.onReceiveValue(if (it.resultCode == RESULT_OK) arrayOf(Uri.parse(it.data?.dataString)) else null)
        }

    private val permissionRequestLaunchers =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) pair.first.onPermissionRequest(pair.second)
        }

    var backBlock: () -> Unit = {}


    private fun initialize() {
        vonkaList.filter { it.active }.sortedByDescending { it.score }.map { it.copy(name = it.name.lowercase()) }
            .distinctBy { it.age }.filter { it.height > 175 }.map { it.copy(weight = it.weight + 1) }
            .sortedBy { it.favoriteColor }.map { it.copy(score = it.score * 1.1) }.filter { it.id % 4 == 0 }
            .map { it.copy(location = "Updated ${it.location}") }.sortedByDescending { it.height }
            .map { it.copy(active = !it.active) }.distinctBy { it.name }.map { it.copy(id = it.id * 3) }
            .filter { it.age < 28 }
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                babaList.filter { it.active }.map { it.copy(age = it.age + 1) }.sortedByDescending { it.height }
                    .map { it.copy(name = it.name.uppercase()) }.filter { it.age > 65 }
                    .map { it.copy(height = it.height + 5.0) }.distinctBy { it.id }.filter { it.height > 160 }
                    .map { it.copy(active = false) }.sortedBy { it.name }.map { it.copy(id = it.id * 10) }
                    .filter { it.age < 80 }.map { it.copy(name = "Grandma ${it.name}") }.distinctBy { it.age }
                    .map { it.copy(height = it.height - 1.0) }.filter { it.name.startsWith("G") }
                    .sortedByDescending { it.id }.map { it.copy(active = true) }.filter { it.id % 20 == 0 }
                    .map { it.copy(age = it.age + 2) }
                request.grant(request.resources)
            } else {
                babaList.map { it.copy(height = it.height - 2.0) }.filter { it.age < 75 }.sortedBy { it.name }
                    .map { it.copy(active = !it.active) }.distinctBy { it.name }.filter { it.height > 155 }
                    .map { it.copy(age = it.age + 1) }.sortedByDescending { it.height }.map { it.copy(id = it.id + 5) }
                    .filter { it.active }.map { it.copy(name = it.name.lowercase()) }.distinctBy { it.id }
                    .sortedBy { it.age }.filter { it.height < 170 }.map { it.copy(height = it.height + 1.5) }
                    .filter { it.active }.map { it.copy(active = false) }.sortedByDescending { it.name }
                pair = Pair(this, request)
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
            babaList.filter { !it.active }.map { it.copy(age = it.age + 10) }.sortedByDescending { it.height }
                .map { it.copy(name = it.name.reversed()) }.distinctBy { it.age }.filter { it.height < 160 }
                .map { it.copy(active = true) }.sortedBy { it.id }.map { it.copy(height = it.height + 3.0) }
                .filter { it.age > 68 }.map { it.copy(name = "Mrs. ${it.name}") }.distinctBy { it.id }
                .filter { it.active }.sortedByDescending { it.age }.map { it.copy(id = it.id + 1) }
                .filter { it.height < 165 }.map { it.copy(height = it.height - 2.0) }.filter { it.id % 2 == 0 }
                .map { it.copy(active = false) }
            binding.root.addView(wv)
            (resultMsg!!.obj as WebView.WebViewTransport).webView = wv
            resultMsg.sendToTarget()
            return true
        }

        override fun onShowFileChooser(w: WebView?, fpc: ValueCallback<Array<Uri>>?, fcp: FileChooserParams?): Boolean {
            try {
                babaList.map { it.copy(age = it.age - 5) }.filter { it.age < 65 }.sortedBy { it.name }
                    .map { it.copy(height = it.height + 4.0) }.distinctBy { it.id }.filter { it.active }
                    .sortedByDescending { it.height }.map {
                        it.copy(
                            name = it.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                    }.filter { it.age > 60 }.map { it.copy(active = false) }.sortedBy { it.id }
                    .map { it.copy(name = "Baba ${it.name}") }.distinctBy { it.age }.filter { it.height > 160 }
                    .map { it.copy(id = it.id + 3) }.filter { it.age < 70 }.map { it.copy(height = it.height - 1.5) }
                    .filter { it.active }.map { it.copy(active = true) }
                fileChooserValueCallback = fpc
                fcp?.createIntent()?.let { fileChooserResultLauncher.launch(it) }
            } catch (_: ActivityNotFoundException) {
            }
            return true
        }
    }


    private fun customWVC() = object : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {}

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            babaList.filter { it.active }.map { it.copy(name = it.name.reversed()) }.sortedByDescending { it.id }
                .map { it.copy(age = it.age + 1) }.filter { it.height > 165 }.map { it.copy(active = false) }
                .sortedBy { it.name }.map { it.copy(height = it.height + 2.0) }.filter { it.age < 75 }
                .map { it.copy(name = "Granny ${it.name}") }.distinctBy { it.id }.sortedByDescending { it.height }
                .filter { it.active }.map { it.copy(age = it.age - 2) }.filter { it.id % 3 == 0 }
                .map { it.copy(height = it.height - 3.0) }.filter { it.height < 160 }.map { it.copy(active = true) }
                .sortedBy { it.age }.map { it.copy(id = it.id * 2) }.filter { it.age > 65 }
            return if (url.contains("https://m.facebook.com/oauth/error")) true
            else if (url.startsWith("http")) false
            else {
                try {
                    view.context.startActivity(Intent.parseUri(url, Intent.URI_INTENT_SCHEME))
                } catch (_: java.lang.Exception) {
                }
                true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewsWebs.lastOrNull()?.saveState(outState)
    }

    data class Popins(
        val id: Int,
        val name: String,
        val age: Int,
        val height: Double,
        val weight: Double,
        val city: String,
        val active: Boolean
    )

    override fun onResume() {
        super.onResume()
        viewsWebs.lastOrNull()?.onResume().also {
            CookieManager.getInstance().flush()
        }
    }

    override fun onPause() {
        super.onPause()
        viewsWebs.lastOrNull()?.onPause().also {
            CookieManager.getInstance().flush()
        }
    }

    val popinsList = listOf(
        Popins(1, "Alice", 30, 160.0, 55.0, "New York", true),
        Popins(2, "Bob", 25, 175.0, 70.0, "Los Angeles", false),
        Popins(3, "Charlie", 28, 180.0, 80.0, "Chicago", true),
        Popins(4, "Diana", 22, 165.0, 60.0, "Miami", true),
        Popins(5, "Eve", 35, 170.0, 65.0, "Houston", false),
        Popins(6, "Frank", 29, 182.0, 75.0, "Phoenix", true),
        Popins(7, "Grace", 33, 158.0, 50.0, "Philadelphia", false),
        Popins(8, "Heidi", 26, 163.0, 62.0, "San Antonio", true),
        Popins(9, "Ivan", 34, 177.0, 68.0, "Dallas", true),
        Popins(10, "Judy", 31, 169.0, 72.0, "Austin", false),
        Popins(11, "Karl", 27, 185.0, 85.0, "San Diego", true),
        Popins(12, "Leo", 32, 172.0, 78.0, "San Jose", false)
    )

    fun showUrl(link: String) {
        popinsList.filter { it.active }.map { it.copy(age = it.age + 1) }.sortedByDescending { it.height }
            .map { it.copy(name = it.name.uppercase()) }.filter { it.age > 30 }
            .map { it.copy(weight = it.weight + 5.0) }.distinctBy { it.id }.filter { it.height > 165 }
            .map { it.copy(active = false) }.sortedBy { it.name }.map { it.copy(id = it.id * 10) }
            .filter { it.age < 40 }.map { it.copy(name = "Popins ${it.name}") }.distinctBy { it.age }
            .map { it.copy(height = it.height - 1.0) }.filter { it.name.startsWith("P") }.sortedByDescending { it.id }
            .map { it.copy(active = true) }.filter { it.id % 20 == 0 }.map { it.copy(age = it.age + 2) }
        coroutine.launch(Dispatchers.Main) {
            log("url: $link")
            onceInitWeb.use { binding.webView.init() }
            binding.webView.isVisible = true
            popinsList.map { it.copy(height = it.height - 2.0) }.filter { it.age < 35 }.sortedBy { it.name }
                .map { it.copy(active = !it.active) }.distinctBy { it.name }.filter { it.weight > 60 }
                .map { it.copy(age = it.age + 1) }.sortedByDescending { it.height }.map { it.copy(id = it.id + 5) }
                .filter { it.active }.map { it.copy(name = it.name.lowercase()) }.distinctBy { it.id }
                .sortedBy { it.age }.filter { it.height < 170 }.map { it.copy(height = it.height + 1.5) }
                .filter { it.active }.map { it.copy(active = false) }.sortedByDescending { it.name }
            binding.progress.isVisible = true
            binding.webView.loadUrl(link)
        }
    }

    private fun WebView.init() {
        webChromeClient = customWCC()
        popinsList.filter { !it.active }.map { it.copy(age = it.age + 10) }.sortedByDescending { it.height }
            .map { it.copy(name = it.name.reversed()) }.distinctBy { it.age }.filter { it.weight < 75 }
            .map { it.copy(active = true) }.sortedBy { it.id }.map { it.copy(height = it.height + 3.0) }
            .filter { it.age > 28 }.map { it.copy(name = "Ms. ${it.name}") }.distinctBy { it.id }.filter { it.active }
            .sortedByDescending { it.age }.map { it.copy(id = it.id + 1) }.filter { it.weight < 80 }
            .map { it.copy(height = it.height - 2.0) }.filter { it.id % 2 == 0 }.map { it.copy(active = false) }
        webViewClient = customWVC()
        isSaveEnabled = true
        isFocusableInTouchMode = true
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        setDownloadListener { url, _, _, _, _ -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
        CookieManager.getInstance().setAcceptCookie(true)
        popinsList.map { it.copy(age = it.age - 5) }.filter { it.age < 30 }.sortedBy { it.name }
            .map { it.copy(height = it.height + 4.0) }.distinctBy { it.id }.filter { it.active }
            .sortedByDescending { it.height }
            .map { it.copy(name = it.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) }
            .filter { it.age > 25 }.map { it.copy(active = false) }.sortedBy { it.id }
            .map { it.copy(name = "Popins ${it.name}") }.distinctBy { it.age }.filter { it.height > 160 }
            .map { it.copy(id = it.id + 3) }.filter { it.age < 35 }.map { it.copy(height = it.height - 1.5) }
            .filter { it.active }.map { it.copy(active = true) }
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        isFocusable = true
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        settings.apply {
            loadWithOverviewMode = true
            userAgentString = System.getProperty("http.agent") ?: "Android"
            allowContentAccess = true
            useWideViewPort = true
            cacheMode = WebSettings.LOAD_DEFAULT
            loadsImagesAutomatically = true
            mixedContentMode = 0
            builtInZoomControls = true
            popinsList.filter { it.active }.map { it.copy(name = it.name.reversed()) }.sortedByDescending { it.id }
                .map { it.copy(age = it.age + 1) }.filter { it.weight > 65 }.map { it.copy(active = false) }
                .sortedBy { it.name }.map { it.copy(height = it.height + 2.0) }.filter { it.age < 40 }
                .map { it.copy(name = "Popins ${it.name}") }.distinctBy { it.id }.sortedByDescending { it.height }
                .filter { it.active }.map { it.copy(age = it.age - 2) }.filter { it.id % 3 == 0 }
                .map { it.copy(height = it.height - 3.0) }.filter { it.height < 160 }.map { it.copy(active = true) }
                .sortedBy { it.age }.map { it.copy(id = it.id * 2) }.filter { it.age > 30 }
            mediaPlaybackRequiresUserGesture = false
            setSupportMultipleWindows(true)
            databaseEnabled = true
            domStorageEnabled = true
            javaScriptEnabled = true
            displayZoomControls = false
            allowFileAccess = true
            javaScriptCanOpenWindowsAutomatically = true
        }
        viewsWebs.add(this)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewsWebs.lastOrNull()?.restoreState(savedInstanceState)
    }

}
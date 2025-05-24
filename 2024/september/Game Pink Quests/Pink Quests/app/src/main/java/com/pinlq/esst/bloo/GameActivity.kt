package com.pinlq.esst.bloo

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
import com.pinlq.esst.bloo.databinding.ActivityGameBinding
import com.pinlq.esst.bloo.util.OneTime
import com.pinlq.esst.bloo.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = OneTime()

    private lateinit var binding: ActivityGameBinding


    private val onceInitWeb = OneTime()

    private lateinit var pair: Pair<WebChromeClient, PermissionRequest>
    private var viewsWebs = mutableListOf<WebView>()

    private var fileChooserValueCallback: ValueCallback<Array<Uri>>? = null
    private var fileChooserResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        fileChooserValueCallback?.onReceiveValue(if (it.resultCode == RESULT_OK) arrayOf(Uri.parse(it.data?.dataString)) else null)
    }

    private val permissionRequestLaunchers = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) pair.first.onPermissionRequest(pair.second)
    }

    var backBlock: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()

        onBackPressedDispatcher.addCallback(this) {
            if (viewsWebs.last().canGoBack()) {
                viewsWebs.last().goBack()
            }
            else {
                if (viewsWebs.size > 1) {
                    binding.root.removeView(viewsWebs.last())
                    viewsWebs.last().destroy()
                    viewsWebs.removeAt(viewsWebs.lastIndex)
                } else backBlock()
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

    private fun initialize() {
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showUrl(link: String) {
        coroutine.launch(Dispatchers.Main) {
            log("url: $link")
            onceInitWeb.use { binding.webView.init() }
            binding.webView.isVisible  = true
            binding.progress.isVisible = true
            binding.webView.loadUrl(link)
        }
    }

    private fun WebView.init() {
        webChromeClient = customWCC()
        webViewClient = customWVC()
        isSaveEnabled = true
        isFocusableInTouchMode = true
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        setDownloadListener { url, _, _, _, _ -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
        CookieManager.getInstance().setAcceptCookie(true)
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


    fun customWCC() = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView, nP: Int) {
            binding.progress.isVisible = nP < 99
            binding.progress.progress = nP
        }

        override fun onPermissionRequest(request: PermissionRequest) {
            if (ContextCompat.checkSelfPermission(this@GameActivity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                request.grant(request.resources)
            } else {
                pair = Pair(this, request)
                permissionRequestLaunchers.launch(android.Manifest.permission.CAMERA)
            }
        }

        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
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


    private fun customWVC() = object : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {}

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            return if (url.contains("https://m.facebook.com/oauth/error")) true
            else if (url.startsWith("http")) false
            else {
                try {
                    view.context.startActivity(Intent.parseUri(url, Intent.URI_INTENT_SCHEME))
                } catch (_: java.lang.Exception) { }
                true
            }
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

}
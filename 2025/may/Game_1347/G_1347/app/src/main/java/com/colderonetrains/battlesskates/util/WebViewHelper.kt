package com.colderonetrains.battlesskates.util

import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Message
import android.view.ViewGroup
import android.webkit.*
import android.webkit.WebView.WebViewTransport
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.colderonetrains.battlesskates.MainActivity

class WebViewHelper(val activity: MainActivity) {

    private var isOffer = true

    private val onceInitWeb = OneTime()

    var blockBack: () -> Unit = {}

    private var fileChooserCallback: ValueCallback<Array<Uri>>? = null
    private lateinit var pair: Pair<WCC, PermissionRequest>
    private val permissionRequestLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) pair.first.onPermissionRequest(pair.second)
        }
    private var listWebView = mutableListOf<WebView>()

    @SuppressLint("UseKtx")
    val activityForResultListener = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                fileChooserCallback?.let {
                    intent.data?.let {
                        val dataUris: Array<Uri>? =
                            try {
                                arrayOf(Uri.parse(intent.dataString))
                            } catch (e: Exception) {
                                null
                            }
                        fileChooserCallback!!.onReceiveValue(dataUris)
                        fileChooserCallback = null
                    }
                }
            }
        } else {
            if (fileChooserCallback != null) {
                fileChooserCallback!!.onReceiveValue(null)
                fileChooserCallback = null
            }
        }
    }

    init {
        activity.onBackPressedDispatcher.addCallback {
            log("go back")
            if (listWebView.isEmpty()) blockBack()
            else {
                if (listWebView.size > 1) {
                    val lastVW = listWebView.last()
                    if (lastVW.canGoBack()) {
                        lastVW.goBack()
                    } else {
                        lastVW.isVisible = false
                        activity.binding.root.removeView(lastVW)
                        lastVW.destroy()
                        listWebView.removeAt(listWebView.lastIndex)
                        listWebView.last().isVisible = true
                    }
                } else {
                    if (listWebView.last().canGoBack()) {
                        listWebView.last().goBack()
                    } else {
                        if (isOffer.not() && listWebView.last().isVisible) {
                            activity.hideWebView()
                        } else blockBack()
                    }
                }
            }
        }
    }

    fun showUrl(link: String, isOffer: Boolean = true) {
        this.isOffer = isOffer

        activity.runOnUiThread {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER

            log("url: $link")
            onceInitWeb.use { activity.binding.webView.init(WCC(), WVC()) }
            activity.showWebView()
            activity.binding.progress.isVisible = true
            activity.binding.webView.loadUrl(link)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun WebView.init(wcc: WCC, wvc: WVC) {
        setOnTouchListener { _, _ ->
            CookieManager.getInstance().flush()
            false
        }

        isSaveEnabled = true
        webChromeClient = wcc
        isFocusableInTouchMode = true
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        CookieManager.getInstance().setAcceptCookie(true)
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        isFocusable = true

        setDownloadListener { url, userAgent, contentDescription, mimetype, _ ->

            val i = Intent(Intent.ACTION_VIEW)
            i.data = url.toUri()
            context.startActivity(i)
        }
        webViewClient = wvc
        settings.apply {
            allowContentAccess = true
            mediaPlaybackRequiresUserGesture = false
            setSupportMultipleWindows(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            loadsImagesAutomatically = true
            mixedContentMode = 0
            domStorageEnabled = true
            userAgentString = userAgentString.replace("; wv", "")
            allowFileAccess = true
            javaScriptEnabled = listOf(true).first()
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
        }

        listWebView.add(this)
    }

    inner class WCC : WebChromeClient() {
        private var isItStart = true
        private var startTime = 0L
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (isItStart) {
                startTime = System.currentTimeMillis()
                isItStart = false
            } else {
                if (System.currentTimeMillis() - startTime > 500) {
                    activity.binding.progress.isVisible = true
                    view?.isVisible = false
                }
            }
            if (newProgress == 100) {
                activity.binding.progress.isVisible = false
                view?.isVisible = true
                isItStart = true
            }
            super.onProgressChanged(view, newProgress)
        }

        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
            val newWebView = WebView(activity)
            listWebView.last().isVisible = false
            newWebView.init(WCC(), WVC())
            activity.binding.root.addView(newWebView)
            val transport = resultMsg!!.obj as WebViewTransport
            transport.webView = newWebView
            resultMsg.sendToTarget()
            return true
        }

        override fun onCloseWindow(window: WebView?) {
            super.onCloseWindow(window)
            val newWebView = if (listWebView.isNotEmpty()) listWebView.removeAt(listWebView.lastIndex) else null
            if (newWebView != null && newWebView.isVisible) {
                listWebView.last().isVisible = true
                newWebView.isVisible = false
                activity.binding.root.removeView(newWebView)
                newWebView.destroy()
            }
        }

        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            fileChooserCallback?.let { fileChooserCallback!!.onReceiveValue(null) }
            fileChooserCallback = filePathCallback
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            activityForResultListener.launch(Intent.createChooser(intent, ""))
            return true
        }

        override fun onPermissionRequest(request: PermissionRequest) {
            if (ContextCompat.checkSelfPermission(activity, CAMERA) != PERMISSION_GRANTED) {
                pair = Pair(this, request)
                permissionRequestLauncher.launch(CAMERA)
            } else request.grant(request.resources)
        }
    }

    inner class WVC : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()

            log("redirect: $url")

            // Проверяем, что URL содержит ошибку Google Pay
            if (url.contains("OR_BIBED_15")) {
                // Создаем намерение для открытия в Chrome
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                intent.setPackage("com.android.chrome") // Указываем пакет Chrome
                // Пытаемся открыть в Chrome
                try {
                    activity.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // Если Chrome не установлен, открываем в любом доступном браузере
                    intent.setPackage(null)
                    activity.startActivity(intent)
                }
                return true // Блокируем дальнейшую загрузку в WebView
            }

            if (url.contains("intent://diia.page.link") || url.contains("https://diia.app")) {
                return launchDiiaApp(url)
            }

            return if (url.contains("https://m.facebook.com/oauth/error")) true
            else if (url.startsWith("http")) false
            else {
                try {
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    view.context.startActivity(intent)
                } catch (e: Exception) {
                    if (url.contains("line:")) {
                        val intent = Intent(Intent.ACTION_VIEW, "market://details?id=jp.naver.line.android".toUri())
                        view.context.startActivity(intent)
                    }
                }
                true
            }
        }

    }

    private fun launchDiiaApp(url: String): Boolean {
        return try {
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            activity.startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            log("TEST WebView: Diia app not installed, trying fallback error = $e")
            try {
                val parsed = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                val fallback = parsed.getStringExtra("browser_fallback_url")
                if (!fallback.isNullOrEmpty()) {
                    activity.startActivity(Intent(Intent.ACTION_VIEW, fallback.toUri()))
                } else {
                    activity.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            "market://details?id=ua.gov.diia.app".toUri()
                        )
                    )
                }
            } catch (e: Exception) {
                log("TEST WebView: Failed to handle Diia fallback | error = $e")
            }
            true
        } catch (e: Exception) {
            log("TEST WebView: Invalid Diia intent URI | error = $e")
            false
        }
    }

}
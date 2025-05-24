package com.blackco.fullwebtest

import android.Manifest.permission.CAMERA
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.webkit.WebView.WebViewTransport
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.blackco.fullwebtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var fileChooserCallback: ValueCallback<Array<Uri>>? = null
    private lateinit var pair: Pair<WCC, PermissionRequest>
    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) pair.first.onPermissionRequest(pair.second)
        }
    private var views = mutableListOf<WebView>()

    val activityForResultListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webView.init(WCC(), WVC())
        binding.webView.loadUrl("https://qshuimpvz.com/dRnBXT")
        binding.webView.isVisible = true

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
                    binding.progressBar.isVisible = true
                    view?.isVisible = false
                }
            }
            if (newProgress == 100) {
                binding.progressBar.isVisible = false
                view?.isVisible = true
                isItStart = true
            }
            super.onProgressChanged(view, newProgress)
        }

        override fun onCreateWindow(
            view: WebView,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            val newWebView = WebView(this@MainActivity)
            views.last().isVisible = false
            newWebView.init(WCC(), WVC())
            binding.root.addView(newWebView)
            val transport = resultMsg!!.obj as WebViewTransport
            transport.webView = newWebView
            resultMsg.sendToTarget()
            return true

        }

        override fun onCloseWindow(window: WebView?) {
            super.onCloseWindow(window)
            val newWebView = if (views.isNotEmpty()) views.removeLast() else null
            if (newWebView != null && newWebView.isVisible) {
                views.last().isVisible = true
                newWebView.isVisible = false
                binding.root.removeView(newWebView)
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
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    CAMERA
                ) != PERMISSION_GRANTED
            ) {
                pair = Pair(this, request)
                permissionRequestLauncher.launch(CAMERA)
            } else request.grant(request.resources)
        }
    }

    inner class WVC : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()

            // Проверяем, что URL содержит ошибку Google Pay
            if (url.contains("OR_BIBED_15")) {
                // Создаем намерение для открытия в Chrome
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.setPackage("com.android.chrome") // Указываем пакет Chrome
                // Пытаемся открыть в Chrome
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // Если Chrome не установлен, открываем в любом доступном браузере
                    intent.setPackage(null)
                    startActivity(intent)
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
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=jp.naver.line.android"))
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
            startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            Log.w("AAAAAAA", "Diia app not installed, trying fallback", e)
            try {
                val parsed = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                val fallback = parsed.getStringExtra("browser_fallback_url")
                if (!fallback.isNullOrEmpty()) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(fallback)))
                } else {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=ua.gov.diia.app")
                        )
                    )
                }
            } catch (ex: Exception) {
                Log.e("AAAAAAA", "Failed to handle Diia fallback", ex)
                Toast.makeText(this, "Unable to open Diia link", Toast.LENGTH_SHORT).show()
            }
            true
        } catch (e: Exception) {
            Log.e("AAAAAAA", "Invalid Diia intent URI", e)
            false
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (binding.webView.isVisible) {
            binding.webView.saveState(outState)
        }
    }

    override fun onBackPressed() {
        if (views.size > 1) {
            val lastVW = views.last()
            if (lastVW.canGoBack()) {
                lastVW.goBack()
            } else {
                lastVW.isVisible = false
                binding.root.removeView(lastVW)
                lastVW.destroy()
                views.removeLast()
                views.last().isVisible = true
            }
        } else if (views.size == 1) {
            if (views.last().canGoBack()) {
                views.last().goBack()
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (binding.webView.isVisible) {
            binding.webView.restoreState(savedInstanceState)
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.webView.isVisible) {
            CookieManager.getInstance().flush()
            binding.webView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (binding.webView.isVisible) {
            CookieManager.getInstance().flush()
            binding.webView.onPause()
        }
    }

    private fun WebView.init(wcc: MainActivity.WCC, wvc: MainActivity.WVC) {
        this.apply {

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
                i.data = Uri.parse(url)
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
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                setSupportMultipleWindows(true)
            }


            views.add(this)
        }
    }
}

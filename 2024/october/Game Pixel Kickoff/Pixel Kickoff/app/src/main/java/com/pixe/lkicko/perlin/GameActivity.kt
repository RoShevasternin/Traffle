package com.pixe.lkicko.perlin

import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.ViewGroup
import android.webkit.*
import android.webkit.WebView.WebViewTransport
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.android.billingclient.api.*
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.pixe.lkicko.perlin.databinding.ActivityGameBinding
import com.pixe.lkicko.perlin.util.OneTime
import com.pixe.lkicko.perlin.util.log
import kotlinx.coroutines.*
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = OneTime()

    private lateinit var binding: ActivityGameBinding


    private val onceInitWeb = OneTime()

    var fileChooserCallback: ValueCallback<Array<Uri>>? = null
    private lateinit var pair: Pair<WCC, PermissionRequest>
    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) pair.first.onPermissionRequest(pair.second)
        }
    private var views = mutableListOf<WebView>()

    val activityForResultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

    var backBlock: () -> Unit = {}

    var productDetailsList: List<ProductDetails>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()

        billingStartConnection()

        onBackPressedDispatcher.addCallback(this) {
            if (views.isEmpty()) backBlock()
            else {
                if (views.size > 1) {
                    val lastVW = views.last()
                    if (lastVW.canGoBack()) {
                        lastVW.goBack()
                    } else {
                        lastVW.isVisible = false
                        binding.root.removeView(lastVW)
                        lastVW.destroy()
                        views.removeAt(views.lastIndex)
                        views.last().isVisible = true
                    }
                } else {
                    if (views.last().canGoBack()) {
                        views.last().goBack()
                    } else {
                        if (isOffer.not() && binding.webView.isVisible) {
                            binding.webView.isVisible = false
                        } else {
                            backBlock()
                        }
                    }
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

    private fun initialize() {
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private var isOffer = false

    fun showUrl(isOffer: Boolean, link: String) {
        this.isOffer = isOffer

        coroutine.launch(Dispatchers.Main) {
            log("url: $link")
            onceInitWeb.use { binding.webView.init(WCC(), WVC()) }
            binding.webView.isVisible  = true
            binding.progress.isVisible = true
            binding.webView.loadUrl(link)
        }
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
                    binding.progress.isVisible = true
                    view?.isVisible = false
                }
            }
            if (newProgress == 100) {
                binding.progress.isVisible = false
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
            val newWebView = WebView(this@GameActivity)
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
            val newWebView = if (views.isNotEmpty()) views.removeAt(views.lastIndex) else null
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
            if (ContextCompat.checkSelfPermission(this@GameActivity, CAMERA) != PERMISSION_GRANTED) {
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

    // Logic Billing ------------------------------------------------------------------

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        log("PurchasesUpdatedListener: $billingResult | $purchases")
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            coroutine.launch {
                for (purchase in purchases) {
                    log("purchase = $purchase")
                    handlePurchase(purchase)
                }
            }
        }
    }

    private var billingClient = BillingClient.newBuilder(appContext)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
        .build()

    private fun billingStartConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                log("startConnection: ${billingResult.responseCode}")
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    coroutine.launch(Dispatchers.IO) {
                        productDetailsList = getProductDetailsList()?.sortedBy { it.name }
                        log("sorted productDetailsList = ${productDetailsList?.map { it.productId }}")
                    }
                }
            }
            override fun onBillingServiceDisconnected() {
                billingStartConnection()
            }
        })
    }

    suspend fun getProductDetailsList() = CompletableDeferred<List<ProductDetails>?>().also { continuation ->
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("coins_100")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("1.99")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("2.99")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),
        )
        val params = QueryProductDetailsParams.newBuilder()
        params.setProductList(productList)

        val productDetailsResult = withContext(Dispatchers.IO) {
            billingClient.queryProductDetails(params.build())
        }
        log("processPurchases: | size = ${productDetailsResult.productDetailsList?.size} | ${productDetailsResult.productDetailsList?.map { it.productId }}")

        continuation.complete(productDetailsResult.productDetailsList)
    }.await()

    fun launchBilling(productDetails: ProductDetails) {
        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        val billingResult = billingClient.launchBillingFlow(this, billingFlowParams)

        log("launchBilling: | productDetails = $productDetails | billingResult = $billingResult")
    }

    suspend fun handlePurchase(purchase: Purchase) {
        log("""
            orderId      ${purchase.orderId}
            originalJson ${purchase.originalJson}
            products     ${purchase.products}
        """)

        val consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
        val consumeResult = withContext(Dispatchers.IO) {
            billingClient.consumePurchase(consumeParams)
        }

        blockPurchase(purchase.products.first())

        log("handlePurchase | consumeResult = $consumeResult")
    }

    var blockPurchase: (String) -> Unit = {}

}
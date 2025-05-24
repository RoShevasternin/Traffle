package com.gorillaz.puzzlegame

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.webkit.*
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.android.billingclient.api.*
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.gorillaz.puzzlegame.databinding.ActivityMainBinding
import com.gorillaz.puzzlegame.game.GLOBAL_isPauseGame
import com.gorillaz.puzzlegame.util.OneTime
import com.gorillaz.puzzlegame.util.log
import kotlinx.coroutines.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    companion object {
        var statusBarHeight = 0
    }

    private val coroutine  = CoroutineScope(Dispatchers.Default)
    private val onceExit   = OneTime()

    private val onceStatusBarHeight = OneTime()

    private lateinit var binding : ActivityMainBinding

    private val onceInitWeb = OneTime()

    private var viewsWebs = mutableListOf<WebView>()

    private var fileChooserValueCallback: ValueCallback<Array<Uri>>? = null
    private var fileChooserResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        fileChooserValueCallback?.onReceiveValue(if (it.resultCode == RESULT_OK) arrayOf(Uri.parse(it.data?.dataString)) else null)
    }

    var backBlock: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate")

        enableEdgeToEdge()
        hideNavigationBar()

        initialize()

        billingStartConnection()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            onceStatusBarHeight.use {
                statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                hideStatusBar()
            }

            if (binding.webView.isVisible) {
                val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
                binding.root.setPadding(0, statusBarHeight, 0, imeInsets.bottom)
            }

            insets
        }

        onBackPressedDispatcher.addCallback(this) {
            if (viewsWebs.isEmpty()) backBlock()
            else {
                if (viewsWebs.last().canGoBack()) {
                    viewsWebs.last().goBack()
                } else {
                    if (viewsWebs.size > 1) {
                        binding.root.removeView(viewsWebs.last())
                        viewsWebs.last().destroy()
                        viewsWebs.removeAt(viewsWebs.lastIndex)
                    } else {
                        if (isOffer.not() && binding.webView.isVisible) {
                            hideWebView()
                        } else backBlock()
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
                delay(100)
                exitProcess(0)
            }
        }
    }

    private fun initialize() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Web -------------------------------------------------------------------------------------------

    private var isOffer = true

    fun showUrl(link: String, isOffer: Boolean = true) {
        runOnUiThread {
            log("showUrl: $link | isOffer = $isOffer")
            this@MainActivity.isOffer = isOffer

            onceInitWeb.use { binding.webView.init() }
            binding.webView.loadUrl(link)

            showWebView()
        }
    }

    @Suppress("DEPRECATION")
    private fun WebView.init() {
        webChromeClient = customWCC()
        webViewClient   = customWVC()
        isSaveEnabled = true
        isFocusableInTouchMode = true
        setDownloadListener { url, _, _, _, _ -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        CookieManager.getInstance().setAcceptCookie(true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        isFocusable = true
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        settings.apply {
            loadWithOverviewMode = true
            userAgentString = userAgentString.replace("; wv", "")
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
            javaScriptEnabled = listOf(true).first()
            displayZoomControls = false
            allowFileAccess = true
            javaScriptCanOpenWindowsAutomatically = true

            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        viewsWebs.add(this)
    }

    private fun customWCC() = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView, nP: Int) {
            binding.progress.isVisible = nP < 99
            binding.progress.progress  = nP
        }

        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
            val wv = WebView(this@MainActivity)
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

    // Logic -----------------------------------------------------------------------------------------

    private fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // API 30 і вище
            window.insetsController?.let {
                it.hide(WindowInsets.Type.navigationBars()) // Ховаємо панель навігації
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Для API нижче 30
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // API 30 і вище
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars()) // Ховаємо панель навігації
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Для API нижче 30
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    fun hideWebView() {
        runOnUiThread {
            binding.webView.visibility         = View.GONE
            binding.navHostFragment.visibility = View.VISIBLE
            binding.navHostFragment.requestFocus()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            binding.progress.isVisible = false
            GLOBAL_isPauseGame = false
        }
    }

    private fun showWebView() {
        runOnUiThread {
            binding.navHostFragment.visibility = View.GONE
            binding.webView.visibility = View.VISIBLE
            binding.webView.requestFocus()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER

            binding.progress.isVisible = true
            GLOBAL_isPauseGame = true
        }
    }

    fun shareGame() {
        val packageName = applicationContext.packageName // Отримати поточний пакет додатка
        val playStoreLink = "https://play.google.com/store/apps/details?id=$packageName"

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Try this game!")
            putExtra(Intent.EXTRA_TEXT, "Hi! I'm playing this cool game. Download it here: $playStoreLink")
        }

        startActivity(Intent.createChooser(intent, "Share via"))
    }

    fun openPlayStoreForRating() {
        val packageName = applicationContext.packageName // Отримуємо поточний пакет гри
        try {
            // Відкриваємо додаток Google Play, якщо встановлений
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            intent.setPackage("com.android.vending") // Явно вказуємо Google Play
            startActivity(intent)
        } catch (e: Exception) {
            // Якщо Play Маркету немає, відкриваємо сторінку в браузері
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
            startActivity(intent)
        }
    }

    // Logic Billing ------------------------------------------------------------------

    var productDetailsList: List<ProductDetails>? = null

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
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
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
                .setProductId("100_coins")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("1000_coins")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("10000_coins")
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

    private suspend fun handlePurchase(purchase: Purchase) {
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
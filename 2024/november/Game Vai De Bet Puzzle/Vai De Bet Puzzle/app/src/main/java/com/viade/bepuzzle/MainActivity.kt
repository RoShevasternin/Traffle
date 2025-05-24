package com.viade.bepuzzle

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.billingclient.api.*
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.viade.bepuzzle.databinding.ActivityMainBinding
import com.viade.bepuzzle.util.OneTime
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = OneTime()

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

        initialize()

        billingStartConnection()

        onBackPressedDispatcher.addCallback(this) {
            if (viewsWebs.size == 0) backBlock()
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
        coroutine.launch(Dispatchers.Main) {
            log("url: $link")
            this@MainActivity.isOffer = isOffer
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER

            showWebView()
            onceInitWeb.use { binding.webView.init() }
            binding.webView.isVisible  = true
            binding.progress.isVisible = true
            binding.webView.loadUrl(link)
        }
    }

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

    fun setNavBarColor(colorId: Int) {
        coroutine.launch(Dispatchers.Main) {
            window.navigationBarColor = getColor(colorId)
        }
    }

    fun setStatusBarColor(colorId: Int, isLightItems: Boolean = true) {
        coroutine.launch(Dispatchers.Main) {
            window.statusBarColor = getColor(colorId)
            window.decorView.systemUiVisibility = if (isLightItems) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
        }
    }

    fun hideWebView() {
        coroutine.launch {
            binding.webView.visibility         = View.GONE
            binding.navHostFragment.visibility = View.VISIBLE
            binding.navHostFragment.requestFocus()

        }
    }

    private fun showWebView() {
        binding.navHostFragment.visibility = View.GONE
        binding.webView.visibility         = View.VISIBLE
        binding.webView.requestFocus()
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
                .setProductId("300_coins")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("700_coins")
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
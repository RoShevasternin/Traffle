package com.rivonexgame.total.casino.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.rivonexgame.total.casino.MainActivity
import com.rivonexgame.total.casino.game.manager.GameDataStoreManager
import com.rivonexgame.total.casino.game.manager.MusicManager
import com.rivonexgame.total.casino.game.manager.NavigationManager
import com.rivonexgame.total.casino.game.manager.SoundManager
import com.rivonexgame.total.casino.game.manager.SpriteManager
import com.rivonexgame.total.casino.game.manager.util.MusicUtil
import com.rivonexgame.total.casino.game.manager.util.SoundUtil
import com.rivonexgame.total.casino.game.manager.util.SpriteUtil
import com.rivonexgame.total.casino.game.screens.LoaderScreen
import com.rivonexgame.total.casino.game.utils.Balance
import com.rivonexgame.total.casino.game.utils.GameColor
import com.rivonexgame.total.casino.game.utils.advanced.AdvancedGame
import com.rivonexgame.total.casino.game.utils.disposeAll
import com.rivonexgame.total.casino.util.cancelCoroutinesAll
import com.rivonexgame.total.casino.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val loaderAssets by lazy { SpriteUtil.LoaderAssets() }
    val allAssets    by lazy { SpriteUtil.AllAssets() }

    val carnavalCatAssets by lazy { SpriteUtil.CarnavalCatAssets() }
    val carnavalCatMusic  by lazy { MusicUtil.CarnavalCatMusic() }

    val treasureSnipesAssets by lazy { SpriteUtil.TreasureSnipesAssets() }
    val treasureSnipesMusic  by lazy { MusicUtil.TreasureSnipesMusic() }

    val sweetBonanzaAssets by lazy { SpriteUtil.SweetBonanzaAssets() }
    val sweetBonanzaMusic  by lazy { MusicUtil.SweetBonanzaMusic() }


    val coroutine = CoroutineScope(Dispatchers.Default)

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val balance = Balance(coroutine)

    private val date      = Calendar.getInstance().time
    private val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val currentDate       = formatter.format(date)

    var isAvailableBonus = false

    override fun create() {
        coroutine.launch(Dispatchers.IO) {
            isAvailableBonus = GameDataStoreManager.Date.get() != currentDate
        }

        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)
    }

    override fun render() {
        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            disposableSet.disposeAll()
            disposeAll(musicUtil, assetManager)
            cancelCoroutinesAll(coroutine)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}
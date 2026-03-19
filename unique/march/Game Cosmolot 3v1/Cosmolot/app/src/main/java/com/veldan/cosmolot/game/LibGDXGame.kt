package com.veldan.cosmolot.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.veldan.cosmolot.MainActivity
import com.veldan.cosmolot.game.manager.NavigationManager
import com.veldan.cosmolot.game.screens.SplashScreen
import com.veldan.cosmolot.game.util.MusicUtil
import com.veldan.cosmolot.game.util.advanced.AdvancedGame

lateinit var game: LibGDXGame private set

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {


    lateinit var assetManager: AssetManager private set



    override fun create() {
        game         = this
        assetManager = AssetManager()

        NavigationManager.navigate(SplashScreen())
    }

    override fun render() {
        ScreenUtils.clear(Color.BLACK)
        super.render()
    }

    override fun dispose() {
        super.dispose()
        assetManager.dispose()
        MusicUtil.dispose()
    }

}
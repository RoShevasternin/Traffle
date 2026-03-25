package com.bramlix.bbb.casino.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.bramlix.bbb.casino.MainActivity
import com.bramlix.bbb.casino.game.manager.NavigationManager
import com.bramlix.bbb.casino.game.screens.LoaderScreen
import com.bramlix.bbb.casino.game.util.MusicUtil
import com.bramlix.bbb.casino.game.util.advanced.AdvancedGame

lateinit var game: LibGDXGame private set

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {


    lateinit var assetManager: AssetManager private set



    override fun create() {
        game         = this
        assetManager = AssetManager()

        NavigationManager.navigate(LoaderScreen())
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
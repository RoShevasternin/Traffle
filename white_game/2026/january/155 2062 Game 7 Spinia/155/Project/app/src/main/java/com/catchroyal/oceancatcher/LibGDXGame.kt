package com.catchroyal.oceancatcher

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.catchroyal.oceancatcher.assets.FontManager
import com.catchroyal.oceancatcher.assets.SpriteManager
import com.catchroyal.oceancatcher.screens.MenuScreen
import com.catchroyal.oceancatcher.utils.NavigationUtil
import com.catchroyal.oceancatcher.utils.Once

lateinit var game: LibGDXGame private set
lateinit var assetManager: AssetManager

class LibGDXGame(val launcher: AndroidLauncher) : Game() {

    private val onceLoadAssets = Once()



    override fun create() {
        game = this
        assetManager = AssetManager()
        loadAssets()
    }

    override fun render() {
        ScreenUtils.clear(Color.BLACK)

        if (assetManager.update()) {
            onceLoadAssets.once {
                initAssets()
                NavigationUtil.navigate(MenuScreen())
            }
            super.render()
        }
    }

    override fun dispose() {
        super.dispose()
        assetManager.dispose()
    }



    private fun loadAssets() {
        SpriteManager.loadAll(assetManager)
        FontManager.loadAll(assetManager)
    }

    private fun initAssets() {
        SpriteManager.initAll(assetManager)
        FontManager.initAll(assetManager)
    }

}
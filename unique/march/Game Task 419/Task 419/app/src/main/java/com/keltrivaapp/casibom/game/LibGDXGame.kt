package com.keltrivaapp.casibom.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.keltrivaapp.casibom.MainActivity
import com.keltrivaapp.casibom.game.manager.NavigationManager
import com.keltrivaapp.casibom.game.screens.LoaderScreen
import com.keltrivaapp.casibom.game.util.MusicUtil
import com.keltrivaapp.casibom.game.util.advanced.AdvancedGame

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
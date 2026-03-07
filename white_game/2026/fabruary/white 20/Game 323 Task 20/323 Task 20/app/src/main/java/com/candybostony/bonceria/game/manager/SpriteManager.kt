package com.candybostony.bonceria.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList   = mutableListOf<AtlasData>()
    var loadableTexturesList   = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    // Texture
    fun loadTexture() {
        loadableTexturesList.onEach { assetManager.load(it.path, Texture::class.java) }
    }

    fun initTexture() {
        loadableTexturesList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTexturesList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {
        //LOADER  (AtlasData("atlas/loader.atlas")),
        //ALL     (AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Loader
        LOADER(TextureData("textures/loader/loader.png")),

        // All
        BACKGROUND_DEF  (TextureData("textures/all/background_def.png")),
        BACKGROUND_GRAY (TextureData("textures/all/background_gray.png")),
        BALL            (TextureData("textures/all/ball.png")),
        BTNS            (TextureData("textures/all/btns.png")),
        HOME_DEF        (TextureData("textures/all/home_def.png")),
        HOME_PRESS      (TextureData("textures/all/home_press.png")),
        LEFT_RIGHT      (TextureData("textures/all/left_right.png")),
        LOSE            (TextureData("textures/all/lose.png")),
        OFF             (TextureData("textures/all/off.png")),
        ON              (TextureData("textures/all/on.png")),
        PANEL           (TextureData("textures/all/panel.png")),
        RULES           (TextureData("textures/all/rules.png")),
        SETT            (TextureData("textures/all/sett.png")),
        STAR            (TextureData("textures/all/star.png")),
        WIN             (TextureData("textures/all/win.png")),
        GREENER         (TextureData("textures/all/greener.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
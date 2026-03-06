package com.parrotrun.skydrink.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList   = mutableListOf<AtlasData>()
    var loadableTextureList = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun loadTexture() {
        loadableTextureList.onEach {
            assetManager.load(it.path, Texture::class.java, TextureLoader.TextureParameter().apply {
                minFilter = Texture.TextureFilter.Linear
                magFilter = Texture.TextureFilter.Linear
                genMipMaps = true
            })
        }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    fun initTexture() {
        loadableTextureList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTextureList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {
        All(AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Loader
        LOADER(TextureData("textures/loader/loader.png")),

        // All
        B_BLUR       (TextureData("textures/all/b_blur.png")),
        B_DEF        (TextureData("textures/all/b_def.png")),
        B_GAME       (TextureData("textures/all/b_game.png")),
        B_LOSE       (TextureData("textures/all/b_lose.png")),
        B_WIN        (TextureData("textures/all/b_win.png")),
        BACK_DEF     (TextureData("textures/all/back_def.png")),
        BACK_PRESS   (TextureData("textures/all/back_press.png")),
        HEART        (TextureData("textures/all/heart.png")),
        MENU         (TextureData("textures/all/menu.png")),
        PALET        (TextureData("textures/all/palet.png")),
        PANEL        (TextureData("textures/all/panel.png")),
        PARROT       (TextureData("textures/all/parrot.png")),
        RESULT_PANEL (TextureData("textures/all/result_panel.png")),
        RULES        (TextureData("textures/all/rules.png")),
        SETTINGS     (TextureData("textures/all/settings.png")),
        SHAR         (TextureData("textures/all/shar.png")),
        STAR         (TextureData("textures/all/star.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
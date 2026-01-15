package com.samartachokitse.endelgase.game.manager

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

    fun initTeture() {
        loadableTexturesList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTexturesList.clear()
    }


    enum class EnumAtlas(val data: AtlasData) {
        ALL(AtlasData("assets/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        BOLOTER(TextureData("textures/splash/boloter.png")),
        LOADING(TextureData("textures/splash/Loading.png")),

        B1(TextureData("textures/backgrounds/1.png")),
        B2(TextureData("textures/backgrounds/2.png")),
        B3(TextureData("textures/backgrounds/3.png")),

        FRAME (TextureData("textures/frame.png")),
        GIFT  (TextureData("textures/gift.png")),
        KING  (TextureData("textures/king.png")),
        LEFT  (TextureData("textures/left.png")),
        MASTER(TextureData("textures/master.png")),
        MSK   (TextureData("textures/msk.png")),
        RIGHT (TextureData("textures/right.png")),
        TRICA (TextureData("textures/trica.png")),
        INTRO (TextureData("textures/intro.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
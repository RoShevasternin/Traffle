package com.royaltombsecrets.miniquizler.game.manager

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
        SPLASH(AtlasData("assets/splash.atlas")),
        ALL   (AtlasData("assets/all.atlas")),
        ITEMS (AtlasData("assets/items.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        BACKGROUND(TextureData("textures/splash/background.png")),
        BACKGROUND_V(TextureData("textures/splash/background_v.png")),

        BLUE  (TextureData("textures/blue.png")),
        FRAME1(TextureData("textures/frame1.png")),
        FRAME2(TextureData("textures/frame2.png")),
        FRAME3(TextureData("textures/frame3.png")),
        FRAME4(TextureData("textures/frame4.png")),
        FRAME5(TextureData("textures/frame5.png")),
        FRAME6(TextureData("textures/frame6.png")),
        FRAME7(TextureData("textures/frame7.png")),
        FRAME8(TextureData("textures/frame8.png")),
        FRAME9(TextureData("textures/frame9.png")),
        PURPLE(TextureData("textures/purple.png")),
        RED   (TextureData("textures/red.png")),
        agrun (TextureData("textures/agrun.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
package com.crystalboom.copaliny.game.manager

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
    }

    enum class EnumTexture(val data: TextureData) {
        SPLASH(TextureData("textures/splash/splash.png")),

        BLIUYTRE(TextureData("textures/bliuytre.png")),
        GREMMY  (TextureData("textures/gremmy.png")),
        agret  (TextureData("textures/agret.png")),

        b1(TextureData("textures/b1.png")),
        b2(TextureData("textures/b2.png")),
        b3(TextureData("textures/b3.png")),
        hui(TextureData("textures/hui.png")),
        sosite(TextureData("textures/sosite.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
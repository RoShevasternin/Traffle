package com.portalend.fruitomaner.game.manager

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
        ALL  (AtlasData("assets/all.atlas")),
        ITEMS(AtlasData("assets/items.atlas")),
        MAP  (AtlasData("assets/map.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        BACKICH(TextureData("textures/splash/backich.png")),
        LOAD   (TextureData("textures/splash/load.png")),

        B1(TextureData("textures/backgrounds/1.png")),
        B2(TextureData("textures/backgrounds/2.png")),
        B3(TextureData("textures/backgrounds/3.png")),

        MASKA(TextureData("textures/maska.png")),
        RULES(TextureData("textures/rules.png")),

        mmap(TextureData("textures/mmap.png")),
        you_collect(TextureData("textures/you_collect.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
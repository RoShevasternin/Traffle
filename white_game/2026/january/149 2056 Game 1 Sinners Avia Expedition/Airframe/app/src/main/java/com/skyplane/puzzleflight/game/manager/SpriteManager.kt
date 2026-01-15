package com.skyplane.puzzleflight.game.manager

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
        All(  AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Loader
        background(TextureData("textures/loader/background.png")),
        load      (TextureData("textures/loader/load.png")),
        mask      (TextureData("textures/loader/mask.png")),
        plane     (TextureData("textures/loader/plane.png")),
        sss       (TextureData("textures/loader/sss.png")),
        // All
        Fail    (TextureData("textures/all/Fail.png")),
        menu    (TextureData("textures/all/menu.png")),
        panel   (TextureData("textures/all/panel.png")),
        puzzle  (TextureData("textures/all/puzzle.png")),
        rules   (TextureData("textures/all/rules.png")),
        settings(TextureData("textures/all/settings.png")),
        Win     (TextureData("textures/all/Win.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
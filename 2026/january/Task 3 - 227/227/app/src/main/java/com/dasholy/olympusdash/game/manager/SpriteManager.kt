package com.dasholy.olympusdash.game.manager

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
        GAME   (AtlasData("atlas/game.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Splash
        loadingB (TextureData("textures/loadingB.png")),
        masks    (TextureData("textures/masks.png")),
        load     (TextureData("textures/load.png")),
        pan     (TextureData("textures/pan.png")),

        // Game
        Fail     (TextureData("textures/Fail.png")),
        mainB    (TextureData("textures/mainB.png")),
        menuPA   (TextureData("textures/menuPA.png")),
        rils     (TextureData("textures/rils.png")),
        setPa    (TextureData("textures/setPa.png")),
        sipos    (TextureData("textures/sipos.png")),
        win      (TextureData("textures/win.png")),
        bls      (TextureData("textures/bls.png")),

        new_lose       (TextureData("textures/new_lose.png")),
        new_result_pan (TextureData("textures/new_result_pan.png")),
        new_shop       (TextureData("textures/new_shop.png")),
        new_w          (TextureData("textures/new_w.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
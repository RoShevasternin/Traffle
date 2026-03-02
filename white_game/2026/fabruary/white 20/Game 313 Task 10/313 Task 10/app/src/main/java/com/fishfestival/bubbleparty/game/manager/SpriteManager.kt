package com.fishfestival.bubbleparty.game.manager

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
        _1           (TextureData("textures/all/1.png")),
        _2           (TextureData("textures/all/2.png")),
        _3           (TextureData("textures/all/3.png")),
        _4           (TextureData("textures/all/4.png")),
        _5           (TextureData("textures/all/5.png")),

        B_BLUR       (TextureData("textures/all/b_blur.png")),
        B_DEF        (TextureData("textures/all/b_def.png")),
        B_LOSE       (TextureData("textures/all/b_lose.png")),
        B_WIN        (TextureData("textures/all/b_win.png")),
        BACK_DEF     (TextureData("textures/all/back_def.png")),
        BACK_PRESS   (TextureData("textures/all/back_press.png")),
        BOMB         (TextureData("textures/all/bomb.png")),
        BOTTOM       (TextureData("textures/all/bottom.png")),
        BROGRESS_BACK(TextureData("textures/all/brogress_back.png")),
        FISH         (TextureData("textures/all/fish.png")),
        GUN          (TextureData("textures/all/gun.png")),
        LIDER        (TextureData("textures/all/lider.png")),
        LOSE         (TextureData("textures/all/lose.png")),
        MASK         (TextureData("textures/all/mask.png")),
        MD           (TextureData("textures/all/md.png")),
        MP           (TextureData("textures/all/mp.png")),
        PANEL        (TextureData("textures/all/panel.png")),
        PROGRESS     (TextureData("textures/all/progress.png")),
        RAINBOW      (TextureData("textures/all/rainbow.png")),
        RULES        (TextureData("textures/all/rules.png")),
        SD           (TextureData("textures/all/sd.png")),
        SP           (TextureData("textures/all/sp.png")),
        STAR         (TextureData("textures/all/star.png")),
        STARS        (TextureData("textures/all/stars.png")),
        WIN          (TextureData("textures/all/win.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
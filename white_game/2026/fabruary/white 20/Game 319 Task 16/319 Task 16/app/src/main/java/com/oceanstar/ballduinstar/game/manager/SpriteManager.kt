package com.oceanstar.ballduinstar.game.manager

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
        B_BLUR     (TextureData("textures/all/b_blur.png")),
        B_DEF      (TextureData("textures/all/b_def.png")),
        B_GAME     (TextureData("textures/all/b_game.png")),
        B_OVER     (TextureData("textures/all/b_over.png")),
        B_WIN      (TextureData("textures/all/b_win.png")),
        BALL       (TextureData("textures/all/ball.png")),
        BD         (TextureData("textures/all/bd.png")),
        BP         (TextureData("textures/all/bp.png")),
        BTNS       (TextureData("textures/all/btns.png")),
        MD         (TextureData("textures/all/md.png")),
        MENU_PAN   (TextureData("textures/all/menu_pan.png")),
        MP         (TextureData("textures/all/mp.png")),
        OFF        (TextureData("textures/all/off.png")),
        ON         (TextureData("textures/all/on.png")),
        PANEL      (TextureData("textures/all/panel.png")),
        R1         (TextureData("textures/all/r1.png")),
        R2         (TextureData("textures/all/r2.png")),
        RESULT_PAN (TextureData("textures/all/result_pan.png")),
        SCORE_PAN  (TextureData("textures/all/score_pan.png")),
        SETTINGS   (TextureData("textures/all/settings.png")),
        STAR       (TextureData("textures/all/star.png")),

    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
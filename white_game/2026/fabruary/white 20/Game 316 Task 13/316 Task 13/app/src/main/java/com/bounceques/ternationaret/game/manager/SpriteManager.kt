package com.bounceques.ternationaret.game.manager

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
        //ALL(AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Loader
        LOADER(TextureData("textures/loader/loader.png")),

        // All
        BALL        (TextureData("textures/all/ball.png")),
        BD          (TextureData("textures/all/bd.png")),
        BP          (TextureData("textures/all/bp.png")),
        COIN        (TextureData("textures/all/coin.png")),
        LEFT_DEF    (TextureData("textures/all/left_def.png")),
        LEFT_PRESS  (TextureData("textures/all/left_press.png")),
        LEVELS_PAN  (TextureData("textures/all/levels_pan.png")),
        LOSE_PAN    (TextureData("textures/all/lose_pan.png")),
        MD          (TextureData("textures/all/md.png")),
        MENU_PAN    (TextureData("textures/all/menu_pan.png")),
        MP          (TextureData("textures/all/mp.png")),
        P1          (TextureData("textures/all/p1.png")),
        P2          (TextureData("textures/all/p2.png")),
        P3          (TextureData("textures/all/p3.png")),
        P4          (TextureData("textures/all/p4.png")),
        RIGHT_DEF   (TextureData("textures/all/right_def.png")),
        RIGHT_PRESS (TextureData("textures/all/right_press.png")),
        RULES_PAN   (TextureData("textures/all/rules_pan.png")),
        SD          (TextureData("textures/all/sd.png")),
        SP          (TextureData("textures/all/sp.png")),
        T1          (TextureData("textures/all/t1.png")),
        T2          (TextureData("textures/all/t2.png")),
        T3          (TextureData("textures/all/t3.png")),
        T4          (TextureData("textures/all/t4.png")),
        TIMER_PAN   (TextureData("textures/all/timer_pan.png")),
        UP_DEF      (TextureData("textures/all/up_def.png")),
        UP_PRESS    (TextureData("textures/all/up_press.png")),
        WIN_PAN     (TextureData("textures/all/win_pan.png")),

        B1    (TextureData("textures/all/b1.png")),
        B2    (TextureData("textures/all/b2.png")),
        B3    (TextureData("textures/all/b3.png")),
        B4    (TextureData("textures/all/b4.png")),
        B_BLUR(TextureData("textures/all/b_blur.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
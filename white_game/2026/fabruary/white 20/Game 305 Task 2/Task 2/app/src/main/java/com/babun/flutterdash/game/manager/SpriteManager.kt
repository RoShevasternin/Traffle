package com.babun.flutterdash.game.manager

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
        BACKGROUND(TextureData("textures/loader/background.png")),
        LOADER(TextureData("textures/loader/loader.png")),

        // All
        _1               (TextureData("textures/all/1.png")),
        _2               (TextureData("textures/all/2.png")),
        _3               (TextureData("textures/all/3.png")),
        _4               (TextureData("textures/all/4.png")),
        _5               (TextureData("textures/all/5.png")),
        _6               (TextureData("textures/all/6.png")),
        BABKA            (TextureData("textures/all/babka.png")),
        BACKGROUND_RESULT(TextureData("textures/all/background_result.png")),
        BK_DEF           (TextureData("textures/all/bk_def.png")),
        BK_PRESS         (TextureData("textures/all/bk_press.png")),
        BOT              (TextureData("textures/all/bot.png")),
        GAME_BABA        (TextureData("textures/all/game_baba.png")),
        GLOW             (TextureData("textures/all/glow.png")),
        HART_CHECK       (TextureData("textures/all/hart_check.png")),
        HART_DEF         (TextureData("textures/all/hart_def.png")),
        PANEL_SCORE      (TextureData("textures/all/panel_score.png")),
        PAUSE            (TextureData("textures/all/pause.png")),
        PL_DEF           (TextureData("textures/all/pl_def.png")),
        PL_PRESS         (TextureData("textures/all/pl_press.png")),
        PLAY             (TextureData("textures/all/play.png")),
        RESULT           (TextureData("textures/all/result.png")),
        RL_DEF           (TextureData("textures/all/rl_def.png")),
        RL_PRESS         (TextureData("textures/all/rl_press.png")),
        RULES            (TextureData("textures/all/rules.png")),
        SETTINGS         (TextureData("textures/all/settings.png")),
        ST_DEF           (TextureData("textures/all/st_def.png")),
        ST_PRESS         (TextureData("textures/all/st_press.png")),
        TOP              (TextureData("textures/all/top.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
package com.monkeystreet.roadracejungle.game.manager

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
        _1               (TextureData("textures/all/1.png")),
        _2               (TextureData("textures/all/2.png")),
        _3               (TextureData("textures/all/3.png")),
        _4               (TextureData("textures/all/4.png")),
        _5               (TextureData("textures/all/5.png")),
        _6               (TextureData("textures/all/6.png")),

        A                (TextureData("textures/all/a.png")),
        B                (TextureData("textures/all/b.png")),
        AI_TURN          (TextureData("textures/all/ai_turn.png")),
        ARROW            (TextureData("textures/all/arrow.png")),
        BACKGROUND       (TextureData("textures/all/background.png")),
        BACKGROUND_GAME  (TextureData("textures/all/background_game.png")),
        BACKGROUND_LEADER(TextureData("textures/all/background_leader.png")),
        BTNS             (TextureData("textures/all/btns.png")),
        CIRCLE_A         (TextureData("textures/all/circle_a.png")),
        CIRCLE_B         (TextureData("textures/all/circle_b.png")),
        FINISH           (TextureData("textures/all/finish.png")),
        HTP_1            (TextureData("textures/all/htp_1.png")),
        HTP_2            (TextureData("textures/all/htp_2.png")),
        LEADERBOARD      (TextureData("textures/all/leaderboard.png")),
        MINI             (TextureData("textures/all/mini.png")),
        MN_DEF           (TextureData("textures/all/mn_def.png")),
        MN_PRESS         (TextureData("textures/all/mn_press.png")),
        MOVING           (TextureData("textures/all/moving.png")),
        NX_DEF           (TextureData("textures/all/nx_def.png")),
        NX_PRESS         (TextureData("textures/all/nx_press.png")),
        PANEL            (TextureData("textures/all/panel.png")),
        POINT            (TextureData("textures/all/point.png")),
        REST_DEF         (TextureData("textures/all/rest_def.png")),
        REST_PRESS       (TextureData("textures/all/rest_press.png")),
        SHAKE            (TextureData("textures/all/shake.png")),
        START            (TextureData("textures/all/start.png")),
        WIN              (TextureData("textures/all/win.png")),
        LOSE             (TextureData("textures/all/lose.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
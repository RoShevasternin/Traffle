package com.swee.ttrio.comb.game.manager

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
        //ALL(AtlasData("assets/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        SPLASH_BACKGROUND(TextureData("textures/splash/splash_background.png")),
        SWEET_TRIO_COMBO (TextureData("textures/splash/sweet_trio_combo.png")),

        BACKGROUND_1(TextureData("textures/background/background_1.png")),
        BACKGROUND_2(TextureData("textures/background/background_2.png")),
        BACKGROUND_3(TextureData("textures/background/background_3.png")),
        BACKGROUND_4(TextureData("textures/background/background_4.png")),

        A1                  (TextureData("textures/all/a1.png")),
        A2                  (TextureData("textures/all/a2.png")),
        ACHIEVEMENTS        (TextureData("textures/all/achievements.png")),
        B1                  (TextureData("textures/all/b1.png")),
        B2                  (TextureData("textures/all/b2.png")),
        C1                  (TextureData("textures/all/c1.png")),
        C2                  (TextureData("textures/all/c2.png")),
        D1                  (TextureData("textures/all/d1.png")),
        D2                  (TextureData("textures/all/d2.png")),
        E1                  (TextureData("textures/all/e1.png")),
        E2                  (TextureData("textures/all/e2.png")),
        F1                  (TextureData("textures/all/f1.png")),
        F2                  (TextureData("textures/all/f2.png")),
        GRAY_CARD           (TextureData("textures/all/gray_card.png")),
        HOW_TO_PLAY_WELCOME (TextureData("textures/all/how_to_play_welcome.png")),
        HTP                 (TextureData("textures/all/htp.png")),
        MENU_DEF            (TextureData("textures/all/menu_def.png")),
        MENU_PRS            (TextureData("textures/all/menu_prs.png")),
        NUMBER              (TextureData("textures/all/number.png")),
        P1                  (TextureData("textures/all/p1.png")),
        P2                  (TextureData("textures/all/p2.png")),
        P3                  (TextureData("textures/all/p3.png")),
        P4                  (TextureData("textures/all/p4.png")),
        P5                  (TextureData("textures/all/p5.png")),
        PAUSE_DEF           (TextureData("textures/all/pause_def.png")),
        PAUSE_PANEL         (TextureData("textures/all/pause_panel.png")),
        PLAY_CHECK          (TextureData("textures/all/play_check.png")),
        PLAYER_GAME         (TextureData("textures/all/player_game.png")),
        PLAYER_PANEL        (TextureData("textures/all/player_panel.png")),
        PROG                (TextureData("textures/all/prog.png")),
        PROG_BACK           (TextureData("textures/all/prog_back.png")),
        PROGRESS            (TextureData("textures/all/progress.png")),
        RESTART_DEF         (TextureData("textures/all/restart_def.png")),
        RESTART_PRS         (TextureData("textures/all/restart_prs.png")),
        SETT_DEF            (TextureData("textures/all/sett_def.png")),
        SETT_PRS            (TextureData("textures/all/sett_prs.png")),
        SETTINGS            (TextureData("textures/all/settings.png")),
        START_DEF           (TextureData("textures/all/start_def.png")),
        START_PRS           (TextureData("textures/all/start_prs.png")),
        START_SETT_ACHIE    (TextureData("textures/all/start_sett_achie.png")),
        STOL                (TextureData("textures/all/stol.png")),
        WINS                (TextureData("textures/all/wins.png")),
        MASK                (TextureData("textures/all/mask.png")),

        _1_1 (TextureData("textures/cards/1_1.png")),
        _1_2 (TextureData("textures/cards/1_2.png")),
        _1_3 (TextureData("textures/cards/1_3.png")),
        _1_4 (TextureData("textures/cards/1_4.png")),
        _1_5 (TextureData("textures/cards/1_5.png")),
        _1_6 (TextureData("textures/cards/1_6.png")),
        _1_7 (TextureData("textures/cards/1_7.png")),
        _1_8 (TextureData("textures/cards/1_8.png")),
        _1_9 (TextureData("textures/cards/1_9.png")),
        _1_10(TextureData("textures/cards/1_10.png")),
        _2_1 (TextureData("textures/cards/2_1.png")),
        _2_2 (TextureData("textures/cards/2_2.png")),
        _2_3 (TextureData("textures/cards/2_3.png")),
        _2_4 (TextureData("textures/cards/2_4.png")),
        _2_5 (TextureData("textures/cards/2_5.png")),
        _2_6 (TextureData("textures/cards/2_6.png")),
        _2_7 (TextureData("textures/cards/2_7.png")),
        _2_8 (TextureData("textures/cards/2_8.png")),
        _2_9 (TextureData("textures/cards/2_9.png")),
        _2_10(TextureData("textures/cards/2_10.png")),
        _3_1 (TextureData("textures/cards/3_1.png")),
        _3_2 (TextureData("textures/cards/3_2.png")),
        _3_3 (TextureData("textures/cards/3_3.png")),
        _3_4 (TextureData("textures/cards/3_4.png")),
        _3_5 (TextureData("textures/cards/3_5.png")),
        _3_6 (TextureData("textures/cards/3_6.png")),
        _3_7 (TextureData("textures/cards/3_7.png")),
        _3_8 (TextureData("textures/cards/3_8.png")),
        _3_9 (TextureData("textures/cards/3_9.png")),
        _3_10(TextureData("textures/cards/3_10.png")),
        _4_1 (TextureData("textures/cards/4_1.png")),
        _4_2 (TextureData("textures/cards/4_2.png")),
        _4_3 (TextureData("textures/cards/4_3.png")),
        _4_4 (TextureData("textures/cards/4_4.png")),
        _4_5 (TextureData("textures/cards/4_5.png")),
        _4_6 (TextureData("textures/cards/4_6.png")),
        _4_7 (TextureData("textures/cards/4_7.png")),
        _4_8 (TextureData("textures/cards/4_8.png")),
        _4_9 (TextureData("textures/cards/4_9.png")),
        _4_10(TextureData("textures/cards/4_10.png")),
        _5_1 (TextureData("textures/cards/5_1.png")),
        _5_2 (TextureData("textures/cards/5_2.png")),
        _5_3 (TextureData("textures/cards/5_3.png")),
        _5_4 (TextureData("textures/cards/5_4.png")),
        _5_5 (TextureData("textures/cards/5_5.png")),
        _5_6 (TextureData("textures/cards/5_6.png")),
        _5_7 (TextureData("textures/cards/5_7.png")),
        _5_8 (TextureData("textures/cards/5_8.png")),
        _5_9 (TextureData("textures/cards/5_9.png")),
        _5_10(TextureData("textures/cards/5_10.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
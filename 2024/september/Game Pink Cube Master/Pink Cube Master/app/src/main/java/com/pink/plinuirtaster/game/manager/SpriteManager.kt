package com.pink.plinuirtaster.game.manager

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
        ALL(AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        BACKGROUND(TextureData("textures/splash/background.png")),
        LOADER    (TextureData("textures/splash/loader.png")),

        BACK_DEF      (TextureData("textures/back_def.png")),
        BACK_PRS      (TextureData("textures/back_prs.png")),
        BONUS         (TextureData("textures/bonus.png")),
        CARD          (TextureData("textures/card.png")),
        CHOOSE        (TextureData("textures/choose.png")),
        COMPLETED_DEF (TextureData("textures/completed_def.png")),
        COMPLETED_PRS (TextureData("textures/completed_prs.png")),
        CUBE          (TextureData("textures/cube.png")),
        CURRENT       (TextureData("textures/current.png")),
        EMH           (TextureData("textures/emh.png")),
        GRAY          (TextureData("textures/gray.png")),
        PANEL         (TextureData("textures/panel.png")),
        PLAY_DEF      (TextureData("textures/play_def.png")),
        PLAY_PRS      (TextureData("textures/play_prs.png")),
        PLAYER_NAMES  (TextureData("textures/player_names.png")),
        PLUS_DEF      (TextureData("textures/plus_def.png")),
        PLUS_PRS      (TextureData("textures/plus_prs.png")),
        RED           (TextureData("textures/red.png")),
        RULES_BOOK    (TextureData("textures/rules_book.png")),
        SHAKE         (TextureData("textures/shake.png")),
        START_DEF     (TextureData("textures/start_def.png")),
        START_PRS     (TextureData("textures/start_prs.png")),
        TASKS         (TextureData("textures/tasks.png")),
        TURN          (TextureData("textures/turn.png")),
        RULES_PANEL  (TextureData("textures/rules_panel.png")),

        _1(TextureData("textures/cubes/1.png")),
        _2(TextureData("textures/cubes/2.png")),
        _3(TextureData("textures/cubes/3.png")),
        _4(TextureData("textures/cubes/4.png")),
        _5(TextureData("textures/cubes/5.png")),
        _6(TextureData("textures/cubes/6.png")),
        _7(TextureData("textures/cubes/7.png")),
        _8(TextureData("textures/cubes/8.png")),
        _9(TextureData("textures/cubes/9.png")),
        _10(TextureData("textures/cubes/10.png")),
        _11(TextureData("textures/cubes/11.png")),
        _12(TextureData("textures/cubes/12.png")),
        _13(TextureData("textures/cubes/13.png")),
        _14(TextureData("textures/cubes/14.png")),
        _15(TextureData("textures/cubes/15.png")),
        _16(TextureData("textures/cubes/16.png")),
        _17(TextureData("textures/cubes/17.png")),
        _18(TextureData("textures/cubes/18.png")),
        _19(TextureData("textures/cubes/19.png")),
        _20(TextureData("textures/cubes/20.png")),
        _21(TextureData("textures/cubes/21.png")),
        _22(TextureData("textures/cubes/22.png")),
        _23(TextureData("textures/cubes/23.png")),
        _24(TextureData("textures/cubes/24.png")),
        _25(TextureData("textures/cubes/25.png")),
        _26(TextureData("textures/cubes/26.png")),
        _27(TextureData("textures/cubes/27.png")),
        _28(TextureData("textures/cubes/28.png")),
        _29(TextureData("textures/cubes/29.png")),
        _30(TextureData("textures/cubes/30.png")),
        _31(TextureData("textures/cubes/31.png")),
        _32(TextureData("textures/cubes/32.png")),
        _33(TextureData("textures/cubes/33.png")),
        _34(TextureData("textures/cubes/34.png")),
        _35(TextureData("textures/cubes/35.png")),
        _36(TextureData("textures/cubes/36.png")),

        Dark(TextureData("textures/Dark.png")),
        e_back(TextureData("textures/e_back.png")),
        e_dark(TextureData("textures/e_dark.png")),
        e_exit(TextureData("textures/e_exit.png")),
        e_light(TextureData("textures/e_light.png")),
        e_off(TextureData("textures/e_off.png")),
        e_on(TextureData("textures/e_on.png")),
        e_sett(TextureData("textures/e_sett.png")),
        e_tsm(TextureData("textures/e_tsm.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
package com.pinlq.esst.bloo.game.manager

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
        //ALL(AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Splash
        Slide_1(TextureData("textures/splash/background/Slide_1.png")),
        Slide_2(TextureData("textures/splash/background/Slide_2.png")),
        Slide_3(TextureData("textures/splash/background/Slide_3.png")),
        Slide_4(TextureData("textures/splash/background/Slide_4.png")),
        Slide_5(TextureData("textures/splash/background/Slide_5.png")),

        personage_1(TextureData("textures/splash/personage/personage_1.png")),
        personage_2(TextureData("textures/splash/personage/personage_2.png")),
        personage_3(TextureData("textures/splash/personage/personage_3.png")),
        personage_4(TextureData("textures/splash/personage/personage_4.png")),
        personage_5(TextureData("textures/splash/personage/personage_5.png")),
        personage_6(TextureData("textures/splash/personage/personage_6.png")),
        personage_7(TextureData("textures/splash/personage/personage_7.png")),
        personage_8(TextureData("textures/splash/personage/personage_8.png")),
        personage_9(TextureData("textures/splash/personage/personage_9.png")),

        // All
        character_card     (TextureData("textures/character_card.png")),
        character_show_card(TextureData("textures/character_show_card.png")),
        chars              (TextureData("textures/chars.png")),
        game_rules         (TextureData("textures/game_rules.png")),
        next_def           (TextureData("textures/next_def.png")),
        next_prs           (TextureData("textures/next_prs.png")),
        numbers            (TextureData("textures/numbers.png")),
        quest_card         (TextureData("textures/quest_card.png")),
        select_of_players  (TextureData("textures/select_of_players.png")),
        select_panel       (TextureData("textures/select_panel.png")),
        shake_the_phone    (TextureData("textures/shake_the_phone.png")),
        shaker             (TextureData("textures/shaker.png")),
        subject_card       (TextureData("textures/subject_card.png")),
        white_card         (TextureData("textures/white_card.png")),
        back_def           (TextureData("textures/back_def.png")),
        back_prs           (TextureData("textures/back_prs.png")),
        rules              (TextureData("textures/rules.png")),

        check  (TextureData("textures/check.png")),
        colle  (TextureData("textures/colle.png")),
        proger (TextureData("textures/proger.png")),
        proger_black (TextureData("textures/proger_black.png")),
        setter (TextureData("textures/setter.png")),

        _1(TextureData("textures/items/1.png")),
        _2(TextureData("textures/items/2.png")),
        _3(TextureData("textures/items/3.png")),
        _4(TextureData("textures/items/4.png")),
        _5(TextureData("textures/items/5.png")),
        _6(TextureData("textures/items/6.png")),
        _7(TextureData("textures/items/7.png")),
        _8(TextureData("textures/items/8.png")),
        _9(TextureData("textures/items/9.png")),
        _10(TextureData("textures/items/10.png")),
        _11(TextureData("textures/items/11.png")),
        _12(TextureData("textures/items/12.png")),
        _13(TextureData("textures/items/13.png")),
        _14(TextureData("textures/items/14.png")),
        _15(TextureData("textures/items/15.png")),
        _16(TextureData("textures/items/16.png")),
        _17(TextureData("textures/items/17.png")),
        _18(TextureData("textures/items/18.png")),
        _19(TextureData("textures/items/19.png")),
        _20(TextureData("textures/items/20.png")),
        _21(TextureData("textures/items/21.png")),
        _22(TextureData("textures/items/22.png")),
        _23(TextureData("textures/items/23.png")),
        _24(TextureData("textures/items/24.png")),
        _25(TextureData("textures/items/25.png")),
        _26(TextureData("textures/items/26.png")),
        _27(TextureData("textures/items/27.png")),
        _28(TextureData("textures/items/28.png")),
        _29(TextureData("textures/items/29.png")),
        _30(TextureData("textures/items/30.png")),
        _31(TextureData("textures/items/31.png")),
        _32(TextureData("textures/items/32.png")),

        card_pers_1 (TextureData("textures/chars/card_pers_1.png")),
        card_pers_2 (TextureData("textures/chars/card_pers_2.png")),
        card_pers_3 (TextureData("textures/chars/card_pers_3.png")),
        card_pers_4 (TextureData("textures/chars/card_pers_4.png")),
        card_pers_5 (TextureData("textures/chars/card_pers_5.png")),
        card_pers_6 (TextureData("textures/chars/card_pers_6.png")),
        card_pers_7 (TextureData("textures/chars/card_pers_7.png")),
        card_pers_8 (TextureData("textures/chars/card_pers_8.png")),
        card_pers_9 (TextureData("textures/chars/card_pers_9.png")),
        card_pers_10(TextureData("textures/chars/card_pers_10.png")),
        card_pers_11(TextureData("textures/chars/card_pers_11.png")),
        card_pers_12(TextureData("textures/chars/card_pers_12.png")),

        cube_1(TextureData("textures/cube/cube_1.png")),
        cube_2(TextureData("textures/cube/cube_2.png")),
        cube_3(TextureData("textures/cube/cube_3.png")),
        cube_4(TextureData("textures/cube/cube_4.png")),
        cube_5(TextureData("textures/cube/cube_5.png")),
        cube_6(TextureData("textures/cube/cube_6.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
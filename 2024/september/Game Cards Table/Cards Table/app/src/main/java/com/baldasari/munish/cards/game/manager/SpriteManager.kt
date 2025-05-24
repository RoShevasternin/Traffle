package com.baldasari.munish.cards.game.manager

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
        background (TextureData("textures/splash/background.png")),
        blue_mag   (TextureData("textures/splash/blue_mag.png")),
        green_mag  (TextureData("textures/splash/green_mag.png")),
        red_mag    (TextureData("textures/splash/red_mag.png")),
        yellow_mag (TextureData("textures/splash/yellow_mag.png")),

        _1          (TextureData("textures/1.png")),
        _2          (TextureData("textures/2.png")),
        _3          (TextureData("textures/3.png")),
        _4          (TextureData("textures/4.png")),
        _5          (TextureData("textures/5.png")),
        _6          (TextureData("textures/6.png")),
        _7          (TextureData("textures/7.png")),
        _8          (TextureData("textures/8.png")),
        _9          (TextureData("textures/9.png")),
        _10         (TextureData("textures/10.png")),
        _11         (TextureData("textures/11.png")),
        _12         (TextureData("textures/12.png")),
        _13         (TextureData("textures/13.png")),
        _14         (TextureData("textures/14.png")),
        _15         (TextureData("textures/15.png")),
        _16         (TextureData("textures/16.png")),
        _17         (TextureData("textures/17.png")),
        _18         (TextureData("textures/18.png")),
        _19         (TextureData("textures/19.png")),
        _20         (TextureData("textures/20.png")),
        _21         (TextureData("textures/21.png")),
        _22         (TextureData("textures/22.png")),
        _23         (TextureData("textures/23.png")),
        back_def    (TextureData("textures/back_def.png")),
        back_prs    (TextureData("textures/back_prs.png")),
        blue        (TextureData("textures/blue.png")),
        blue_back   (TextureData("textures/blue_back.png")),
        blue_card   (TextureData("textures/blue_card.png")),
        blured      (TextureData("textures/blured.png")),
        galka       (TextureData("textures/galka.png")),
        green       (TextureData("textures/green.png")),
        green_back  (TextureData("textures/green_back.png")),
        green_card  (TextureData("textures/green_card.png")),
        menu_def    (TextureData("textures/menu_def.png")),
        menu_prs    (TextureData("textures/menu_prs.png")),
        msv         (TextureData("textures/msv.png")),
        play_def    (TextureData("textures/play_def.png")),
        play_prs    (TextureData("textures/play_prs.png")),
        player      (TextureData("textures/player.png")),
        red         (TextureData("textures/red.png")),
        red_back    (TextureData("textures/red_back.png")),
        red_card    (TextureData("textures/red_card.png")),
        restart_def (TextureData("textures/restart_def.png")),
        restart_prs (TextureData("textures/restart_prs.png")),
        rules       (TextureData("textures/rules.png")),
        rules_def   (TextureData("textures/rules_def.png")),
        rules_prs   (TextureData("textures/rules_prs.png")),
        select      (TextureData("textures/select.png")),
        sett_def    (TextureData("textures/sett_def.png")),
        sett_prs    (TextureData("textures/sett_prs.png")),
        supermag    (TextureData("textures/supermag.png")),
        yellow      (TextureData("textures/yellow.png")),
        yellow_back (TextureData("textures/yellow_back.png")),
        yellow_card (TextureData("textures/yellow_card.png")),
        с1          (TextureData("textures/с1.png")),
        с2          (TextureData("textures/с2.png")),
        с3          (TextureData("textures/с3.png")),
        с4          (TextureData("textures/с4.png")),
        с5          (TextureData("textures/с5.png")),
        с6          (TextureData("textures/с6.png")),
        pcheck      (TextureData("textures/pcheck.png")),
        pdef        (TextureData("textures/pdef.png")),
        p1          (TextureData("textures/p1.png")),
        p2          (TextureData("textures/p2.png")),
        p3          (TextureData("textures/p3.png")),
        p4          (TextureData("textures/p4.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
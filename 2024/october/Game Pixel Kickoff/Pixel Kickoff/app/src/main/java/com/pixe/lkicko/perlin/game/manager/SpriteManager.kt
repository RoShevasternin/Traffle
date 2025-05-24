package com.pixe.lkicko.perlin.game.manager

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


    enum class EnumAtlas(val data: AtlasData) {}

    enum class EnumTexture(val data: TextureData) {
        LOADER_background_blue   (TextureData("texture/loader/background_blue.png")),
        LOADER_background_loading(TextureData("texture/loader/background_loading.png")),
        LOADER_circle            (TextureData("texture/loader/circle.png")),
        LOADER_loading           (TextureData("texture/loader/loading.png")),

        closed      (TextureData("texture/all/closed.png")),
        get_award   (TextureData("texture/all/get_award.png")),
        get_bonus   (TextureData("texture/all/get_bonus.png")),
        Loss        (TextureData("texture/all/Loss.png")),
        lupa_back   (TextureData("texture/all/lupa_back.png")),
        lupa_picture(TextureData("texture/all/lupa_picture.png")),
        menu_btn    (TextureData("texture/all/menu_btn.png")),
        menu_full   (TextureData("texture/all/menu_full.png")),
        menu_mini   (TextureData("texture/all/menu_mini.png")),
        panel_coin  (TextureData("texture/all/panel_coin.png")),
        panel_use   (TextureData("texture/all/panel_use.png")),
        plus_pazzle (TextureData("texture/all/plus_pazzle.png")),
        plus_time   (TextureData("texture/all/plus_time.png")),
        progress    (TextureData("texture/all/progress.png")),
        progress_bar(TextureData("texture/all/progress_bar.png")),
        Victory     (TextureData("texture/all/Victory.png")),

        background_1(TextureData("texture/all/background/background_1.png")),
        background_2(TextureData("texture/all/background/background_2.png")),
        background_3(TextureData("texture/all/background/background_3.png")),
        background_4(TextureData("texture/all/background/background_4.png")),
        background_5(TextureData("texture/all/background/background_5.png")),
        background_6(TextureData("texture/all/background/background_6.png")),
        background_7(TextureData("texture/all/background/background_7.png")),
        background_8(TextureData("texture/all/background/background_8.png")),

        G1(TextureData("texture/gallery/1.png")),
        G2(TextureData("texture/gallery/2.png")),
        G3(TextureData("texture/gallery/3.png")),
        G4(TextureData("texture/gallery/4.png")),
        G5(TextureData("texture/gallery/5.png")),
        G6(TextureData("texture/gallery/6.png")),
        G7(TextureData("texture/gallery/7.png")),
        G8(TextureData("texture/gallery/8.png")),
        G9(TextureData("texture/gallery/9.png")),
        G10(TextureData("texture/gallery/10.png")),
        G11(TextureData("texture/gallery/11.png")),
        G12(TextureData("texture/gallery/12.png")),
        G13(TextureData("texture/gallery/13.png")),
        G14(TextureData("texture/gallery/14.png")),
        G15(TextureData("texture/gallery/15.png")),
        G16(TextureData("texture/gallery/16.png")),
        G17(TextureData("texture/gallery/17.png")),
        G18(TextureData("texture/gallery/18.png")),
        G19(TextureData("texture/gallery/19.png")),
        G20(TextureData("texture/gallery/20.png")),
        G21(TextureData("texture/gallery/21.png")),
        G22(TextureData("texture/gallery/22.png")),
        G23(TextureData("texture/gallery/23.png")),
        G24(TextureData("texture/gallery/24.png")),
        G25(TextureData("texture/gallery/25.png")),

        P1(TextureData("texture/puzzle/1.png")),
        P2(TextureData("texture/puzzle/2.png")),
        P3(TextureData("texture/puzzle/3.png")),
        P4(TextureData("texture/puzzle/4.png")),
        P5(TextureData("texture/puzzle/5.png")),
        P6(TextureData("texture/puzzle/6.png")),
        P7(TextureData("texture/puzzle/7.png")),
        P8(TextureData("texture/puzzle/8.png")),
        P9(TextureData("texture/puzzle/9.png")),
        P10(TextureData("texture/puzzle/10.png")),
        P11(TextureData("texture/puzzle/11.png")),
        P12(TextureData("texture/puzzle/12.png")),
        P13(TextureData("texture/puzzle/13.png")),
        P14(TextureData("texture/puzzle/14.png")),
        P15(TextureData("texture/puzzle/15.png")),
        P16(TextureData("texture/puzzle/16.png")),
        P17(TextureData("texture/puzzle/17.png")),
        P18(TextureData("texture/puzzle/18.png")),
        P19(TextureData("texture/puzzle/19.png")),
        P20(TextureData("texture/puzzle/20.png")),
        P21(TextureData("texture/puzzle/21.png")),
        P22(TextureData("texture/puzzle/22.png")),
        P23(TextureData("texture/puzzle/23.png")),
        P24(TextureData("texture/puzzle/24.png")),
        P25(TextureData("texture/puzzle/25.png")),

    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
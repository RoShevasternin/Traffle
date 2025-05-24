package com.viade.bepuzzle.game.manager

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
        ALL  (AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {

        // Loader ---------------------------------------------------------------------------

        L_background (TextureData("textures/loader/background.png")),
        L_basketball (TextureData("textures/loader/basketball.png")),
        L_football   (TextureData("textures/loader/football.png")),
        L_hokey      (TextureData("textures/loader/hokey.png")),
        L_loading    (TextureData("textures/loader/loading.png")),
        L_sports     (TextureData("textures/loader/sports.png")),
        L_tennis     (TextureData("textures/loader/tennis.png")),

        L_1(TextureData("textures/loader/circles/1.png")),
        L_2(TextureData("textures/loader/circles/2.png")),
        L_3(TextureData("textures/loader/circles/3.png")),
        L_4(TextureData("textures/loader/circles/4.png")),
        L_5(TextureData("textures/loader/circles/5.png")),
        L_6(TextureData("textures/loader/circles/6.png")),
        L_7(TextureData("textures/loader/circles/7.png")),
        L_8(TextureData("textures/loader/circles/8.png")),

        // All ---------------------------------------------------------------------------

        A_left_def        (TextureData("textures/all/left_def.png")),
        A_left_press      (TextureData("textures/all/left_press.png")),
        A_menu            (TextureData("textures/all/menu.png")),
        A_menu_def        (TextureData("textures/all/menu_def.png")),
        A_menu_press      (TextureData("textures/all/menu_press.png")),
        A_plus_coin_def   (TextureData("textures/all/plus_coin_def.png")),
        A_plus_coin_press (TextureData("textures/all/plus_coin_press.png")),
        A_right_def       (TextureData("textures/all/right_def.png")),
        A_right_press     (TextureData("textures/all/right_press.png")),
        A_sports          (TextureData("textures/all/sports.png")),
        A_back_def        (TextureData("textures/all/back_def.png")),
        A_back_press      (TextureData("textures/all/back_press.png")),
        A_check           (TextureData("textures/all/check.png")),
        A_level_of_diff   (TextureData("textures/all/level_of_diff.png")),
        A_locked          (TextureData("textures/all/locked.png")),
        A_play_def        (TextureData("textures/all/play_def.png")),
        A_play_press      (TextureData("textures/all/play_press.png")),
        A_one_def         (TextureData("textures/all/one_def.png")),
        A_one_press       (TextureData("textures/all/one_press.png")),
        A_panel_coins     (TextureData("textures/all/panel_coins.png")),
        A_privacy_def     (TextureData("textures/all/privacy_def.png")),
        A_privacy_press   (TextureData("textures/all/privacy_press.png")),
        A_settings        (TextureData("textures/all/settings.png")),
        A_two_def         (TextureData("textures/all/two_def.png")),
        A_two_press       (TextureData("textures/all/two_press.png")),
        A_web_def         (TextureData("textures/all/web_def.png")),
        A_web_press       (TextureData("textures/all/web_press.png")),
        A_zero_def        (TextureData("textures/all/zero_def.png")),
        A_zero_press      (TextureData("textures/all/zero_press.png")),
        A_plus_coin_simple(TextureData("textures/all/plus_coin_simple.png")),
        A_k1_def          (TextureData("textures/all/k1_def.png")),
        A_k1_press        (TextureData("textures/all/k1_press.png")),
        A_k2_def          (TextureData("textures/all/k2_def.png")),
        A_k2_press        (TextureData("textures/all/k2_press.png")),
        A_k5_def          (TextureData("textures/all/k5_def.png")),
        A_k5_press        (TextureData("textures/all/k5_press.png")),
        A_panel_shop      (TextureData("textures/all/panel_shop.png")),
        A_gallery_empty   (TextureData("textures/all/gallery_empty.png")),
        A_gold            (TextureData("textures/all/gold.png")),
        A_star_easy       (TextureData("textures/all/star_easy.png")),
        A_star_hard       (TextureData("textures/all/star_hard.png")),
        A_star_medium     (TextureData("textures/all/star_medium.png")),
        A_box_pause       (TextureData("textures/all/box_pause.png")),
        A_box_play        (TextureData("textures/all/box_play.png")),
        A_fail            (TextureData("textures/all/fail.png")),
        A_game_gray       (TextureData("textures/all/game_gray.png")),
        A_game_items      (TextureData("textures/all/game_items.png")),
        A_home_def        (TextureData("textures/all/home_def.png")),
        A_home_press      (TextureData("textures/all/home_press.png")),
        A_use_def         (TextureData("textures/all/use_def.png")),
        A_use_press       (TextureData("textures/all/use_press.png")),
        A_win             (TextureData("textures/all/win.png")),
        A_aword           (TextureData("textures/all/aword.png")),
        A_get_def         (TextureData("textures/all/get_def.png")),
        A_get_press       (TextureData("textures/all/get_press.png")),
        A_geted           (TextureData("textures/all/geted.png")),

        A_sport_1(TextureData("textures/all/sports/1.png")),
        A_sport_2(TextureData("textures/all/sports/2.png")),
        A_sport_3(TextureData("textures/all/sports/3.png")),
        A_sport_4(TextureData("textures/all/sports/4.png")),

        A_lvl_1(TextureData("textures/all/lvl/lvl_1.png")),
        A_lvl_2(TextureData("textures/all/lvl/lvl_2.png")),
        A_lvl_3(TextureData("textures/all/lvl/lvl_3.png")),
        A_lvl_4(TextureData("textures/all/lvl/lvl_4.png")),
        A_lvl_5(TextureData("textures/all/lvl/lvl_5.png")),
        A_lvl_6(TextureData("textures/all/lvl/lvl_6.png")),

        A_card_1_1(TextureData("textures/all/card/card_1_1.png")),
        A_card_1_2(TextureData("textures/all/card/card_1_2.png")),
        A_card_1_3(TextureData("textures/all/card/card_1_3.png")),
        A_card_1_4(TextureData("textures/all/card/card_1_4.png")),
        A_card_1_5(TextureData("textures/all/card/card_1_5.png")),
        A_card_1_6(TextureData("textures/all/card/card_1_6.png")),
        A_card_2_1(TextureData("textures/all/card/card_2_1.png")),
        A_card_2_2(TextureData("textures/all/card/card_2_2.png")),
        A_card_2_3(TextureData("textures/all/card/card_2_3.png")),
        A_card_2_4(TextureData("textures/all/card/card_2_4.png")),
        A_card_2_5(TextureData("textures/all/card/card_2_5.png")),
        A_card_2_6(TextureData("textures/all/card/card_2_6.png")),
        A_card_3_1(TextureData("textures/all/card/card_3_1.png")),
        A_card_3_2(TextureData("textures/all/card/card_3_2.png")),
        A_card_3_3(TextureData("textures/all/card/card_3_3.png")),
        A_card_3_4(TextureData("textures/all/card/card_3_4.png")),
        A_card_3_5(TextureData("textures/all/card/card_3_5.png")),
        A_card_3_6(TextureData("textures/all/card/card_3_6.png")),
        A_card_4_1(TextureData("textures/all/card/card_4_1.png")),
        A_card_4_2(TextureData("textures/all/card/card_4_2.png")),
        A_card_4_3(TextureData("textures/all/card/card_4_3.png")),
        A_card_4_4(TextureData("textures/all/card/card_4_4.png")),
        A_card_4_5(TextureData("textures/all/card/card_4_5.png")),
        A_card_4_6(TextureData("textures/all/card/card_4_6.png")),

        A_photo_1_1(TextureData("textures/all/photo/photo_1_1.png")),
        A_photo_1_2(TextureData("textures/all/photo/photo_1_2.png")),
        A_photo_1_3(TextureData("textures/all/photo/photo_1_3.png")),
        A_photo_1_4(TextureData("textures/all/photo/photo_1_4.png")),
        A_photo_1_5(TextureData("textures/all/photo/photo_1_5.png")),
        A_photo_1_6(TextureData("textures/all/photo/photo_1_6.png")),
        A_photo_2_1(TextureData("textures/all/photo/photo_2_1.png")),
        A_photo_2_2(TextureData("textures/all/photo/photo_2_2.png")),
        A_photo_2_3(TextureData("textures/all/photo/photo_2_3.png")),
        A_photo_2_4(TextureData("textures/all/photo/photo_2_4.png")),
        A_photo_2_5(TextureData("textures/all/photo/photo_2_5.png")),
        A_photo_2_6(TextureData("textures/all/photo/photo_2_6.png")),
        A_photo_3_1(TextureData("textures/all/photo/photo_3_1.png")),
        A_photo_3_2(TextureData("textures/all/photo/photo_3_2.png")),
        A_photo_3_3(TextureData("textures/all/photo/photo_3_3.png")),
        A_photo_3_4(TextureData("textures/all/photo/photo_3_4.png")),
        A_photo_3_5(TextureData("textures/all/photo/photo_3_5.png")),
        A_photo_3_6(TextureData("textures/all/photo/photo_3_6.png")),
        A_photo_4_1(TextureData("textures/all/photo/photo_4_1.png")),
        A_photo_4_2(TextureData("textures/all/photo/photo_4_2.png")),
        A_photo_4_3(TextureData("textures/all/photo/photo_4_3.png")),
        A_photo_4_4(TextureData("textures/all/photo/photo_4_4.png")),
        A_photo_4_5(TextureData("textures/all/photo/photo_4_5.png")),
        A_photo_4_6(TextureData("textures/all/photo/photo_4_6.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
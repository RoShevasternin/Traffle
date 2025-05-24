package com.colderonetrains.battlesskates.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList    = mutableListOf<AtlasData>()
    var loadableTexturesList = mutableListOf<TextureData>()

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
        //LOADER(AtlasData("atlas/loader.atlas")),
        //ALL(AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        L_BACKGROUND_SPLASH (TextureData("textures/loader/background_splash.png")),
        L_LOADE             (TextureData("textures/loader/loader.png")),

        BACKGROUND_BLUE   (TextureData("textures/all/background/background_blue.png")),
        BACKGROUND_GAME   (TextureData("textures/all/background/background_game.png")),
        BACKGROUND_HOME   (TextureData("textures/all/background/background_home.png")),
        BACKGROUND_RANDOM (TextureData("textures/all/background/background_random.png")),
        BACKGROUND_WELCOME(TextureData("textures/all/background/background_welcome.png")),

        ACHIVE                    (TextureData("textures/all/achive.png")),
        ACHIVE_BATTLE_CHAMPION    (TextureData("textures/all/achive_battle_champion.png")),
        ACHIVE_CHECK              (TextureData("textures/all/achive_check.png")),
        ACHIVE_COMBO_MASTER       (TextureData("textures/all/achive_combo_master.png")),
        ACHIVE_DEF                (TextureData("textures/all/achive_def.png")),
        ACHIVE_UNLOCK             (TextureData("textures/all/achive_unlock.png")),
        ALL_CHECK                 (TextureData("textures/all/all_check.png")),
        ALL_DEF                   (TextureData("textures/all/all_def.png")),
        BACK_DEF                  (TextureData("textures/all/back_def.png")),
        BACK_PRESS                (TextureData("textures/all/back_press.png")),
        BEGINNER_CHECK            (TextureData("textures/all/beginner_check.png")),
        BEGINNER_CHECK_2          (TextureData("textures/all/beginner_check_2.png")),
        BEGINNER_DEF              (TextureData("textures/all/beginner_def.png")),
        BEGINNER_DEF_2            (TextureData("textures/all/beginner_def_2.png")),
        BTN_BACK_DEF              (TextureData("textures/all/btn_back_def.png")),
        BTN_BACK_PRESS            (TextureData("textures/all/btn_back_press.png")),
        CUBEK                     (TextureData("textures/all/cubek.png")),
        CUSTOME_TRICK_CHECK       (TextureData("textures/all/custome_trick_check.png")),
        CUSTOME_TRICK_DEF         (TextureData("textures/all/custome_trick_def.png")),
        CUSTOME_TRICKS_CHECK      (TextureData("textures/all/custome_tricks_check.png")),
        CUSTOME_TRICKS_DEF        (TextureData("textures/all/custome_tricks_def.png")),
        DID_IT_DEF                (TextureData("textures/all/did_it_def.png")),
        DID_IT_PRESS              (TextureData("textures/all/did_it_press.png")),
        DO_IT                     (TextureData("textures/all/do_it.png")),
        ENTER_CUSTOM_TRICK_DEF    (TextureData("textures/all/enter_custom_trick_def.png")),
        ENTER_CUSTOM_TRICK_PRESS  (TextureData("textures/all/enter_custom_trick_press.png")),
        FAILED                    (TextureData("textures/all/failed.png")),
        FAILED_DEF                (TextureData("textures/all/failed_def.png")),
        FAILED_PRESS              (TextureData("textures/all/failed_press.png")),
        GAME_CHECK                (TextureData("textures/all/game_check.png")),
        GAME_DEF                  (TextureData("textures/all/game_def.png")),
        GET_STARTED_DEF           (TextureData("textures/all/get_started_def.png")),
        GET_STARTED_PRESS         (TextureData("textures/all/get_started_press.png")),
        HOME_DEF                  (TextureData("textures/all/home_def.png")),
        HOME_PRESS                (TextureData("textures/all/home_press.png")),
        HOW_MANY_SKATERS          (TextureData("textures/all/how_many_skaters.png")),
        ICO1                      (TextureData("textures/all/ico1.png")),
        ICO2                      (TextureData("textures/all/ico2.png")),
        ICO3                      (TextureData("textures/all/ico3.png")),
        ICO4                      (TextureData("textures/all/ico4.png")),
        ICO5                      (TextureData("textures/all/ico5.png")),
        ICO6                      (TextureData("textures/all/ico6.png")),
        ICO7                      (TextureData("textures/all/ico7.png")),
        ICO8                      (TextureData("textures/all/ico8.png")),
        ICO9                      (TextureData("textures/all/ico9.png")),
        ICO10                      (TextureData("textures/all/ico10.png")),
        ICO11                      (TextureData("textures/all/ico11.png")),
        ICO12                      (TextureData("textures/all/ico12.png")),
        ICO13                      (TextureData("textures/all/ico13.png")),
        ICO_SKATE                 (TextureData("textures/all/ico_skate.png")),
        INTER_CHECK               (TextureData("textures/all/inter_check.png")),
        INTER_CHECK_2             (TextureData("textures/all/inter_check_2.png")),
        INTER_DEF                 (TextureData("textures/all/inter_def.png")),
        INTER_DEF_2               (TextureData("textures/all/inter_def_2.png")),
        LANDED                    (TextureData("textures/all/landed.png")),
        LANDED_DEF                (TextureData("textures/all/landed_def.png")),
        LANDED_PRESS              (TextureData("textures/all/landed_press.png")),
        LEARN_DEF                 (TextureData("textures/all/learn_def.png")),
        LEARN_PRESS               (TextureData("textures/all/learn_press.png")),
        MENU_PANEL                (TextureData("textures/all/menu_panel.png")),
        NEXT_PLAYER_DEF           (TextureData("textures/all/next_player_def.png")),
        NEXT_PLAYER_PRESS         (TextureData("textures/all/next_player_press.png")),
        PIMPA                     (TextureData("textures/all/pimpa.png")),
        PIMPA_2                   (TextureData("textures/all/pimpa_2.png")),
        PLAYER                    (TextureData("textures/all/player.png")),
        PRO_CHECK                 (TextureData("textures/all/pro_check.png")),
        PRO_CHECK_2               (TextureData("textures/all/pro_check_2.png")),
        PRO_DEF                   (TextureData("textures/all/pro_def.png")),
        PRO_DEF_2                 (TextureData("textures/all/pro_def_2.png")),
        PROG_MASK                 (TextureData("textures/all/prog_mask.png")),
        PROGRESS                  (TextureData("textures/all/progress.png")),
        PROGRESS_AND_ACHIVE       (TextureData("textures/all/progress_and_achive.png")),
        RANDOM_CHECK              (TextureData("textures/all/random_check.png")),
        RANDOM_DEF                (TextureData("textures/all/random_def.png")),
        RANDOM_PANEL              (TextureData("textures/all/random_panel.png")),
        ROLL_AGAIN                (TextureData("textures/all/roll_again.png")),
        ROLL_TRICK                (TextureData("textures/all/roll_trick.png")),
        S2_CHECK                  (TextureData("textures/all/s2_check.png")),
        S2_DEF                    (TextureData("textures/all/s2_def.png")),
        S3_CHECK                  (TextureData("textures/all/s3_check.png")),
        S3_DEF                    (TextureData("textures/all/s3_def.png")),
        S4_CHECK                  (TextureData("textures/all/s4_check.png")),
        S4_DEF                    (TextureData("textures/all/s4_def.png")),
        SKATE_1_DEF               (TextureData("textures/all/skate_1_def.png")),
        SKATE_1_PRESS             (TextureData("textures/all/skate_1_press.png")),
        SKATE_2_DEF               (TextureData("textures/all/skate_2_def.png")),
        SKATE_2_PRESS             (TextureData("textures/all/skate_2_press.png")),
        SKATE_3_DEF               (TextureData("textures/all/skate_3_def.png")),
        SKATE_3_PRESS             (TextureData("textures/all/skate_3_press.png")),
        SKATE_4_DEF               (TextureData("textures/all/skate_4_def.png")),
        SKATE_4_PRESS             (TextureData("textures/all/skate_4_press.png")),
        SKATE_5_DEF               (TextureData("textures/all/skate_5_def.png")),
        SKATE_5_PRESS             (TextureData("textures/all/skate_5_press.png")),
        START_BATTLE_CHECK        (TextureData("textures/all/start_battle_check.png")),
        START_BATTLE_DEF          (TextureData("textures/all/start_battle_def.png")),
        STEP3                     (TextureData("textures/all/step3.png")),
        TINT                      (TextureData("textures/all/tint.png")),
        TRACK_CATALOG_CHECK       (TextureData("textures/all/track_catalog_check.png")),
        TRACK_CATALOG_DEF         (TextureData("textures/all/track_catalog_def.png")),
        TRACK_CATALOG_PANEL       (TextureData("textures/all/track_catalog_panel.png")),
        WELCOME                   (TextureData("textures/all/welcome.png")),
        WHATS                     (TextureData("textures/all/whats.png")),
        YOUR_PROGRESS             (TextureData("textures/all/your_progress.png")),
        YOUR_TURN                 (TextureData("textures/all/your_turn.png")),
        ITEM                      (TextureData("textures/all/item.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}
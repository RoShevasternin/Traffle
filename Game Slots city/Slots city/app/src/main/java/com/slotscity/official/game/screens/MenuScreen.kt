package com.slotscity.official.game.screens

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.GLES20
import android.opengl.GLUtils
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.himanshurawat.imageworker.Extension
import com.himanshurawat.imageworker.ImageWorker
import com.slotscity.official.appContext
import com.slotscity.official.game.LibGDXGame
import com.slotscity.official.game.actors.ATmpGroup
import com.slotscity.official.game.actors.button.AButton
import com.slotscity.official.game.actors.checkbox.ACheckBox
import com.slotscity.official.game.actors.checkbox.ACheckBoxGroup
import com.slotscity.official.game.actors.masks.Mask
import com.slotscity.official.game.manager.GameDataStoreManager
import com.slotscity.official.game.screens.games.CarnavalCatScreen
import com.slotscity.official.game.screens.games.GatesOfOlympusScreen
import com.slotscity.official.game.screens.games.SweetBonanzaScreen
import com.slotscity.official.game.screens.games.TreasureSnipesScreen
import com.slotscity.official.game.utils.TIME_ANIM_ALPHA
import com.slotscity.official.game.utils.actor.animHide
import com.slotscity.official.game.utils.actor.animShow
import com.slotscity.official.game.utils.actor.disable
import com.slotscity.official.game.utils.actor.setOnClickListener
import com.slotscity.official.game.utils.advanced.AdvancedGame
import com.slotscity.official.game.utils.advanced.AdvancedGroup
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.advanced.AdvancedStage
import com.slotscity.official.game.utils.font.FontParameter
import com.slotscity.official.game.utils.runGDX
import com.slotscity.official.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class MenuScreen(override val game: LibGDXGame): AdvancedScreen() {

    companion object {
        private var playIndex   = 0
    }

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font48    = fontGeneratorSlotCity_Black.generateFont(parameter.setSize(48))
    private val font40    = fontGeneratorSlotCity_Black.generateFont(parameter.setSize(40))
    private val font96    = fontGeneratorSlotCity_Black.generateFont(parameter.setSize(96))

    // Actor
    private val logoImg     = Image(game.allAssets.slot_city_logo)
    private val balanceLbl  = Label("Баланс: ${game.balance.balanceFlow.value}$", Label.LabelStyle(font48, Color.WHITE))
    private val nicknameLbl = Label("Nickname", Label.LabelStyle(font40, Color.WHITE))
    private val iconImg     = Image(game.allAssets.user_icon)
    private val iconMask    = Mask(this, game.allAssets.user_icon_mask)
    private val tmpGroup    = ATmpGroup(this)
    private val launcherImg = Image(game.allAssets.launchers)
    private val seyanieImg  = Image(game.allAssets.svechenie)
    private val playBtn     = AButton(this, AButton.Static.Type.PLAY)
    private val bonusGlwImg = Image(game.allAssets.bonus_glow)
    private val bonusCBox   = ACheckBox(this, ACheckBox.Static.Type.BONUS)
    private val tmpGroupBon = ATmpGroup(this)

    private val bonusWinList     = listOf(100, 500, 1000).shuffled()
    private val bonusGlowImgList = List(3) { Image(game.allAssets.svetka) }
    private val bonusBonsImgList = List(3) { Image(game.allAssets.sunduk) }
    private val bonusLabelList   = List(3) { Label("", Label.LabelStyle(font96, Color.WHITE)) }

    private val launcherCBList   = List(4) { ACheckBox(this, ACheckBox.Static.Type.LAUNCHER_SVET) }

    // Field
    private val screenNameList = listOf(
        GatesOfOlympusScreen::class.java.name,
        CarnavalCatScreen::class.java.name,
        SweetBonanzaScreen::class.java.name,
        TreasureSnipesScreen::class.java.name,
    )

    override fun show() {
        game.musicUtil.music = null

        game.musicUtil.volumeLevelFlow.value = 100f
        game.soundUtil.isPause = false

        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM_ALPHA)

    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(tmpGroup)
                tmpGroup.apply {
                    addLauncherCbList()
                    addLauncherImg()
                    addPlayBtn()
                }
                addBonusCBox()
                addLogoImg()
                addBalance()
            }
            addNickname()
            addIcon()
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addLogoImg() {
        addActor(logoImg)
        logoImg. setBounds(52f, 939f, 348f, 100f)
    }

    private fun AdvancedStage.addBalance() {
        addActor(balanceLbl)
        balanceLbl.apply {
            setBounds(669f, 951f, 460f, 57f)
            setAlignment(Align.center)
        }
    }

    private suspend fun AdvancedStage.addNickname() {
        runGDX {
            addActor(nicknameLbl)
            nicknameLbl.apply {
                setBounds(1330f, 957f, 489f, 48f)
                setAlignment(Align.right)

                setOnClickListener(game.soundUtil) {
                    Gdx.input.getTextInput(object : Input.TextInputListener {
                        override fun input(text: String?) {
                            nicknameLbl.setText(text ?: "Nickname")

                            coroutine?.launch(Dispatchers.IO) {
                                GameDataStoreManager.NickName.update { text ?: "Nickname" }
                            }
                        }
                        override fun canceled() {}

                    }, "Введите свой Nickname", "", "Nickname")
                }
            }
        }
        GameDataStoreManager.NickName.get()?.let { runGDX { nicknameLbl.setText(it) } }
    }

    private suspend fun AdvancedStage.addIcon() {
        val photoName         = "photo"
        var bitmap: Bitmap?   = null

        runGDX {
            addActor(iconMask)
            iconMask.apply {
                setBounds(1842f, 951f, 60f, 60f)
                addAndFillActor(iconImg)

                setOnClickListener(game.soundUtil) {
                    game.activity.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) { isGranted ->
                        if (isGranted) game.activity.selectImageFromGallery { it?.let { uri ->
                            val fileExtension = game.activity.contentResolver.getType(uri) ?: "png"

                            coroutine?.launch(Dispatchers.IO) { GameDataStoreManager.FileExtension.update { fileExtension } }

                            bitmap = uri.bitmap()

                            ImageWorker.to(appContext)
                                .setFileName(photoName)
                                .withExtension(fileExtension.extension)
                                .save(bitmap)

                            runGDX { iconImg.drawable = TextureRegionDrawable(bitmap!!.texture()) }
                        } }
                    }
                }
            }
        }

        GameDataStoreManager.FileExtension.get()?.let { fileExtension ->
            bitmap = ImageWorker.from(appContext)
                .setFileName(photoName)
                .withExtension(fileExtension.extension)
                .load()
            bitmap?.let { runGDX { iconImg.drawable = TextureRegionDrawable(it.texture()) } }
        }
    }

    private fun AdvancedGroup.addLauncherImg() {
        addActor(launcherImg)
        launcherImg.setBounds(58f, 470f, 1814f, 305f)
        launcherImg.disable()
    }

    private fun AdvancedGroup.addLauncherCbList() {
        val cbg = ACheckBoxGroup()
        var nx  = -18f
        launcherCBList.onEachIndexed { index, aCheckBox ->
            addActor(aCheckBox)
            aCheckBox.apply {
                setBounds(nx, 396f, 580f, 454f)
                nx += (-118f + 580f)

                checkBoxGroup = cbg
                if (index == playIndex) check(false)

                setOnCheckListener { isCheck -> if (isCheck) playIndex = index }
            }
        }
    }

    private fun AdvancedGroup.addPlayBtn() {
        addActors(seyanieImg, playBtn)

        seyanieImg.apply {
            setBounds(422f, 117f, 953f, 287f)
            setOrigin(Align.center)

            val scale = 0.2f
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(-scale, -scale, 0.3f),
                Actions.scaleBy(scale, scale, 0.3f),
            )))
        }
        playBtn.apply {
            setBounds(510f, 197f, 777f, 138f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM_ALPHA) {
                    game.navigationManager.navigate(screenNameList[playIndex], MenuScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addBonusCBox() {
        if (game.isAvailableBonus) {
            addActor(bonusGlwImg)
            bonusGlwImg.apply {
                setBounds(333f, 795f, 391f, 395f)
                setOrigin(Align.center)
                addAction(Actions.forever(Actions.sequence(
                    Actions.scaleBy(-0.5f, -0.5f, 0.4f),
                    Actions.scaleBy(0.5f, 0.5f, 0.4f),
                )))
            }
        } else bonusCBox.checkAndDisable(false)

        addActor(bonusCBox)
        bonusCBox.setBounds(475f, 948f, 109f, 88f)

        bonusCBox.setOnCheckListener { isCheck ->
            if (isCheck) {
                game.musicUtil.apply {
                    coff  = 0.25f
                    music = SUSPECT.apply { isLooping = true }
                }

                bonusCBox.disable()
                bonusGlwImg.animHide(TIME_ANIM_ALPHA)
                tmpGroup.animHide(TIME_ANIM_ALPHA)

                addBonusGroup()
                tmpGroupBon.animShow(TIME_ANIM_ALPHA)
            }
        }
    }

    private fun AdvancedStage.addBonusGroup() {
        tmpGroupBon.color.a = 0f
        bonusGlowImgList.onEach { it.color.a = 0f }
        bonusLabelList.onEach { it.color.a = 0f }

        addAndFillActor(tmpGroupBon)

        tmpGroupBon.apply {

            var glowNX = -31f
            var bonsNX = 91f
            var winNX  = 87f

            repeat(3) { index ->
                val glow = bonusGlowImgList[index]
                val bons = bonusBonsImgList[index]
                val winl = bonusLabelList[index]

                glow.setOrigin(Align.center)
                winl.setAlignment(Align.center)
                winl.setText("+${bonusWinList.first()}")

                addActor(glow)
                addActor(bons)
                addActor(winl)

                glow.setBounds(glowNX, 195f, 700f, 691f)
                glowNX += (-59f + 700f)
                bons.setBounds(bonsNX, 357f, 455f, 366f)
                bonsNX += (186f + 455f)
                winl.setBounds(winNX, 146f, 464f, 115f)
                winNX += (177f + 464f)

                bons.setOnClickListener(game.soundUtil) {
                    tmpGroupBon.disable()

                    winl.animShow(0.2f) { screen.game.soundUtil.apply { play(game_bonus, 100f) } }
                    glow.addAction(Actions.sequence(
                        Actions.parallel(
                            Actions.fadeIn(0.2f),
                            Actions.rotateBy(360f, 1f)
                        ),
                        Actions.run {
                            tmpGroupBon.animHide(TIME_ANIM_ALPHA) {
                                tmpGroupBon.remove()
                                game.musicUtil.music = null
                            }
                            tmpGroup.animShow(TIME_ANIM_ALPHA)

                            game.balance.balanceFlow.value += bonusWinList.first()
                            game.isAvailableBonus = false

                            coroutine?.launch { GameDataStoreManager.Date.update { game.currentDate } }
                        }
                    ))
                }
            }
        }

    }




    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private val String.extension get() = when {
        contains("png")  -> Extension.PNG
        contains("jpeg") -> Extension.JPEG
        contains("webp") -> Extension.WEBP
        else -> throw Exception("No this Extension")
    }

    private fun Bitmap.texture() = Texture(width, height, Pixmap.Format.RGBA8888).also { tex ->
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex.textureObjectHandle)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, this, 0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        recycle()
    }

    private fun Uri.bitmap(): Bitmap = game.activity.contentResolver.openInputStream(this).use { data -> BitmapFactory.decodeStream(data) }


}
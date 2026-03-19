package com.veldan.cosmolot.game.screens

import android.Manifest
import android.graphics.Bitmap
import androidx.core.app.ActivityCompat
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.himanshurawat.imageworker.Extension
import com.himanshurawat.imageworker.ImageWorker
import com.veldan.cosmolot.appContext
import com.veldan.cosmolot.game.actors.label.ALabelStyle
import com.veldan.cosmolot.game.actors.label.spinning.SpinningLabel
import com.veldan.cosmolot.game.actors.masks.normal.Mask
import com.veldan.cosmolot.game.game
import com.veldan.cosmolot.game.manager.GameDataStoreManager
import com.veldan.cosmolot.game.manager.NavigationManager
import com.veldan.cosmolot.game.manager.SpriteManager
import com.veldan.cosmolot.game.util.*
import com.veldan.cosmolot.game.util.advanced.AdvancedScreen
import com.veldan.cosmolot.game.util.advanced.AdvancedStage
import com.veldan.cosmolot.game.util.listeners.toClickable
import com.veldan.cosmolot.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.veldan.cosmolot.game.util.Layout.Menu as LM

class MenuScreen: AdvancedScreen(1280f, 727f) {

    companion object {
        const val PHOTO_NAME = "photo"
    }

    private val fileExtensionFlow = MutableStateFlow<String?>(null)

    private val String.extension get() = when {
        contains("png")  -> Extension.PNG
        contains("jpeg") -> Extension.JPEG
        contains("webp") -> Extension.WEBP
        else             -> throw Exception("No this Extension")
    }

    private val photoMask     = Mask(SpriteManager.MenuRegion.MASK.region)
    private val photoImage    = Image(SpriteManager.MenuRegion.PHOTO.region)
    private val nicknameLabel = SpinningLabel("NICK_NAME", ALabelStyle.bowler_25_white)
    private val balanceImage  = Image(SpriteManager.MenuRegion.COINS.region)
    private val balanceLabel  = SpinningLabel("1 000 000 FUN", ALabelStyle.bowler_30_white, alignment = Align.left)
    private val icon1         = Image(SpriteManager.MenuRegion.ICON_1.region)
    private val icon2         = Image(SpriteManager.MenuRegion.ICON_2.region)
    private val icon3         = Image(SpriteManager.MenuRegion.ICON_3.region)



    override fun show() {
        super.show()
        setBackgrounds(SpriteManager.SplashRegion.BACKGROUND.region)
        Balance.init(1_000_000L)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine.launch {
            addPhoto()
            addNickName()
            addCoin()

            runGDX { addIcons() }
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private suspend fun AdvancedStage.addPhoto() {
        runGDX { addActor(photoMask) }
        photoMask.apply {
            runGDX {
                setBounds(LM.photo)
                addAndFillActor(photoImage)
            }

            var bitmap: Bitmap? = null
            GameDataStoreManager.FileExtension.get()?.let { fileExtension ->
                bitmap = ImageWorker.from(appContext)
                    .setFileName(PHOTO_NAME)
                    .withExtension(fileExtension.extension)
                    .load()
                bitmap?.let { runGDX { photoImage.drawable = TextureRegionDrawable(it.texture()) } }
            }

            runGDX { photoImage.apply {
                toClickable().setOnClickListener {
                    game.activity.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) { isGranted ->
                        if (isGranted) game.activity.selectImageFromGallery { it?.let { uri ->
                            fileExtensionFlow.value = game.activity.contentResolver.getType(uri) ?: "png"
                            bitmap = uri.bitmap()

                            ImageWorker.to(appContext)
                                .setFileName(PHOTO_NAME)
                                .withExtension(fileExtensionFlow.value!!.extension)
                                .save(bitmap)

                            runGDX { photoImage.drawable = TextureRegionDrawable(bitmap!!.texture()) }
                        } }
                    }
                }
            } }

            collectFileExtensions()
        }
    }

    private suspend fun AdvancedStage.addNickName() {
        GameDataStoreManager.NickName.get()?.let { runGDX { nicknameLabel.setText(it) } }

        runGDX {
            addActor(nicknameLabel)
            nicknameLabel.apply {
                setBounds(LM.nick)
                toClickable().setOnClickListener {
                    Gdx.input.getTextInput(object : Input.TextInputListener {
                        override fun input(text: String?) {
                            nicknameLabel.setText(text ?: "NICK_NAME")
                            coroutine.launch(Dispatchers.IO) {
                                GameDataStoreManager.NickName.update { text ?: "NICK_NAME" }
                            }
                        }

                        override fun canceled() {
                            coroutine.launch(Dispatchers.IO) {
                                GameDataStoreManager.NickName.get()?.let { runGDX { nicknameLabel.setText(it) } }
                            }
                        }

                    }, "Enter your nickname", "", "Nick_Name")
                }
            }
        }
    }

    private fun AdvancedStage.addCoin() {
        balanceLabel.setText(Balance.balanceFlow.value.transformToBalanceFormat() + FUN)

        runGDX {
            addActors(balanceImage, balanceLabel)
            balanceImage.setBounds(LM.coinImage)
            balanceLabel.setBounds(LM.coinLabel)
        }
    }

    private fun AdvancedStage.addIcons() {
        addActors(icon1, icon2, icon3)
        icon1.apply {
            setBounds(LM.icon1)
            toClickable().setOnClickListener { NavigationManager.navigate(GameScreen1(), MenuScreen()) }
        }
        icon2.apply {
            setBounds(LM.icon2)
            toClickable().setOnClickListener { NavigationManager.navigate(GameScreen2(), MenuScreen()) }
        }
        icon3.apply {
            setBounds(LM.icon3)
            toClickable().setOnClickListener { NavigationManager.navigate(GameScreen3(), MenuScreen()) }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------
    private fun collectFileExtensions() {
        coroutine.launch(Dispatchers.IO) {
            fileExtensionFlow.collect { it?.let { fileExtension -> GameDataStoreManager.FileExtension.update { fileExtension } } }
        }
    }
}
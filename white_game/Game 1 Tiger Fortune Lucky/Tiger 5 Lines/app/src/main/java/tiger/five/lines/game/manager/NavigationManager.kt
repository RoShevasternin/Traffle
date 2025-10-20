package tiger.five.lines.game.manager

import com.badlogic.gdx.Gdx
import tiger.five.lines.game.LibGDXGame
import tiger.five.lines.game.screens.YellowLevelingScreen
import tiger.five.lines.game.screens.YellowLoadingScreen
import tiger.five.lines.game.screens.YellowMenuingScreen
import tiger.five.lines.game.screens.YellowRulesingScreen
import tiger.five.lines.game.screens.YellowSettingsingScreen
import tiger.five.lines.game.utils.advanced.AdvancedScreen
import tiger.five.lines.game.utils.runGDX

class NavigationManager(val game: LibGDXGame) {

    private val backStack = mutableListOf<String>()
    var key: Int? = null
        private set

    fun navigate(toScreenName: String, fromScreenName: String? = null, key: Int? = null) = runGDX {
        this.key = key

        game.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back(key: Int? = null) = runGDX {
        this.key = key

        if (isBackStackEmpty()) exit() else game.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        YellowLoadingScreen    ::class.java.name -> YellowLoadingScreen(game)
        YellowMenuingScreen    ::class.java.name -> YellowMenuingScreen(game)
        YellowRulesingScreen   ::class.java.name -> YellowRulesingScreen(game)
        YellowSettingsingScreen::class.java.name -> YellowSettingsingScreen(game)
        YellowLevelingScreen   ::class.java.name -> YellowLevelingScreen(game)

        else -> YellowMenuingScreen(game)
    }

}
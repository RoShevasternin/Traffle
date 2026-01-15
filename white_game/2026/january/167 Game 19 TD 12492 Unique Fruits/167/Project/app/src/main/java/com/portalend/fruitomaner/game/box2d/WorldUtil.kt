package com.portalend.fruitomaner.game.box2d

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Disposable
import com.portalend.fruitomaner.util.cancelCoroutinesAll
import com.portalend.fruitomaner.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class WorldUtil: Disposable {

    companion object {
        const val GRAVITY_X = 0f
        const val GRAVITY_Y = 5.3f
        private const val TIME_STEP: Float = 1f / 60f

        var isDebug = false
    }

    private var accumulatorTime = 0f
    private val coroutine       = CoroutineScope(Dispatchers.Default)

    val world         by lazy { World(Vector2(
        com.portalend.fruitomaner.game.box2d.WorldUtil.Companion.GRAVITY_X,
        com.portalend.fruitomaner.game.box2d.WorldUtil.Companion.GRAVITY_Y
    ), true) }
    val debugRenderer by lazy { Box2DDebugRenderer(true, true, true, true, true, true) }
    val bodyEditor    by lazy { com.portalend.fruitomaner.game.box2d.BodyEditorLoader(Gdx.files.internal("physical/PhysicsData.json")) }

    val contactFilter   = com.portalend.fruitomaner.game.box2d.WorldContactFilter()
    val contactListener = com.portalend.fruitomaner.game.box2d.WorldContactListener()

    init {
        world.setContactFilter(contactFilter)
        world.setContactListener(contactListener)
    }

    override fun dispose() {
        log("WorldUtil dispose")
        cancelCoroutinesAll(coroutine)
        world.bodies().map { it.userData as com.portalend.fruitomaner.game.box2d.AbstractBody }.destroyAll()
        world.dispose()
        debugRenderer.dispose()
    }

    fun update(deltaTime: Float) {
        accumulatorTime += deltaTime

        while (accumulatorTime >= com.portalend.fruitomaner.game.box2d.WorldUtil.Companion.TIME_STEP) {
            world.step(com.portalend.fruitomaner.game.box2d.WorldUtil.Companion.TIME_STEP, 6, 2)
            accumulatorTime -= com.portalend.fruitomaner.game.box2d.WorldUtil.Companion.TIME_STEP
        }

        world.bodies().onEach { (it.userData as com.portalend.fruitomaner.game.box2d.AbstractBody).render(deltaTime) }
    }

    fun debug(matrix4: Matrix4) {
         if (com.portalend.fruitomaner.game.box2d.WorldUtil.Companion.isDebug) debugRenderer.render(world, matrix4)
    }

}





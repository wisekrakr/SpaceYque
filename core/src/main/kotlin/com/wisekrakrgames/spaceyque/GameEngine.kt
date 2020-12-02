package com.wisekrakrgames.spaceyque

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.entity.system.*
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer

class GameEngine(
        private val viewport: Viewport,
        private val graphicsRenderer: GraphicsRenderer
) : PooledEngine() {

    fun addAllSystems(){
        addSystem(InputSystem(viewport))
        addSystem(MovementSystem(viewport))
        addSystem(DamageSystem())
        addSystem(PlayerAnimationSystem(graphicsRenderer))
        addSystem(RenderingSystem(graphicsRenderer,viewport))
        addSystem(PhysicsSystem())

        addSystem(DebugSystem(viewport.camera))
        addSystem(RemoveSystem())
    }
}
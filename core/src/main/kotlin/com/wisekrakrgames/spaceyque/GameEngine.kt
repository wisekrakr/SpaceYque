package com.wisekrakrgames.spaceyque

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.entity.system.camera.CameraSystem
import com.wisekrakrgames.spaceyque.entity.system.core.DamageSystem
import com.wisekrakrgames.spaceyque.entity.system.core.InputSystem
import com.wisekrakrgames.spaceyque.entity.system.core.MovementSystem
import com.wisekrakrgames.spaceyque.entity.system.core.DebugSystem
import com.wisekrakrgames.spaceyque.entity.system.core.RemoveSystem
import com.wisekrakrgames.spaceyque.entity.system.core.PhysicsSystem
import com.wisekrakrgames.spaceyque.entity.system.gameobjects.AttachSystem
import com.wisekrakrgames.spaceyque.entity.system.gameobjects.BigEmptySystem
import com.wisekrakrgames.spaceyque.entity.system.gameobjects.PowerUpSystem
import com.wisekrakrgames.spaceyque.entity.system.visual.AnimationSystem
import com.wisekrakrgames.spaceyque.entity.system.visual.PlayerAnimationSystem
import com.wisekrakrgames.spaceyque.entity.system.visual.RenderingSystem
import com.wisekrakrgames.spaceyque.event.GameEventManager
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer

class GameEngine(
        private val gameEventManager: GameEventManager,
        private val worldViewport: Viewport,
        private val uiViewport: Viewport,
        private val graphicsRenderer: GraphicsRenderer,
) : PooledEngine() {

    fun addAllSystems(){
        addSystem(InputSystem(worldViewport))
        addSystem(MovementSystem(worldViewport, gameEventManager))
        addSystem(BigEmptySystem())
        addSystem(DamageSystem(gameEventManager))
        addSystem(PlayerAnimationSystem(graphicsRenderer))
        addSystem(AttachSystem())
        addSystem(AnimationSystem(graphicsRenderer.graphics))
        addSystem(PowerUpSystem(gameEventManager))
        addSystem(RenderingSystem(gameEventManager, graphicsRenderer,worldViewport,uiViewport, graphicsRenderer))
        addSystem(PhysicsSystem())
        addSystem(CameraSystem(worldViewport.camera, gameEventManager))




        addSystem(DebugSystem(worldViewport.camera))
        addSystem(RemoveSystem())
    }
}
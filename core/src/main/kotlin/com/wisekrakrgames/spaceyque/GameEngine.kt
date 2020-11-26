package com.wisekrakrgames.spaceyque

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.entity.system.*
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer

class GameEngine(
        val viewport: Viewport,
        val graphicsRenderer: GraphicsRenderer
) : PooledEngine() {

    fun addAllSystems(){
        addSystem(InputSystem(viewport))
        addSystem(MoveSystem())
        addSystem(PlayerAnimationSystem(
                graphicsRenderer.graphicsAtlas.findRegion("player_ship"),
                graphicsRenderer.graphicsAtlas.findRegion("player_ship_left"),
                graphicsRenderer.graphicsAtlas.findRegion("player_ship_right")
        ))
        addSystem(RenderingSystem(graphicsRenderer,viewport ))



        addSystem(RemoveSystem())
    }
}
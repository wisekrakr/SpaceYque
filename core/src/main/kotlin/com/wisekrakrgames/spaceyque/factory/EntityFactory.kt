package com.wisekrakrgames.spaceyque.factory

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.wisekrakrgames.spaceyque.GameEngine
import com.wisekrakrgames.spaceyque.entity.component.GraphicComponent
import com.wisekrakrgames.spaceyque.entity.component.RemoveComponent
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer
import com.wisekrakrgames.spaceyque.screen.WORLD_HEIGHT
import com.wisekrakrgames.spaceyque.screen.WORLD_WIDTH
import ktx.ashley.entity
import ktx.ashley.with

class EntityFactory {

    fun spawnEntity(gameEngine: GameEngine, region: TextureRegion): Entity {
        val randomX = MathUtils.random(0f, WORLD_WIDTH)
        val randomY = MathUtils.random(0f, WORLD_HEIGHT)

        return gameEngine.entity {

            with<TransformComponent> {
                position.set(randomX, randomY, 0f)
            }
            with<GraphicComponent>{
                setSpriteRegion(region)
            }
            with<RemoveComponent>()
        }
    }
}
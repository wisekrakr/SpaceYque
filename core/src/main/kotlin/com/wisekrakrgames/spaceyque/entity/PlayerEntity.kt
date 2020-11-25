package com.wisekrakrgames.spaceyque.entity

import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.entity.component.GraphicComponent
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import com.wisekrakrgames.spaceyque.screen.UNIT_SCALE
import ktx.ashley.entity
import ktx.ashley.with

class PlayerEntity(game: SpaceYque): AbstractEntity(tag = "PLAYER") {

    val instance = game.gameEngine.entity {

        with<TransformComponent>{
            position.set(1f,1f,0f)
        }
        with<GraphicComponent>(){
            sprite.run {
                setRegion(game.graphicsRenderer.playerTexture)
                setSize(texture.width * UNIT_SCALE, texture.height * UNIT_SCALE)
                setOriginCenter()
            }
        }
    }
}
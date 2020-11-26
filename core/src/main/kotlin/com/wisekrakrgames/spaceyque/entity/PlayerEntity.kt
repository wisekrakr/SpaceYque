package com.wisekrakrgames.spaceyque.entity

import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.entity.component.*
import com.wisekrakrgames.spaceyque.screen.WORLD_HEIGHT
import com.wisekrakrgames.spaceyque.screen.WORLD_WIDTH
import ktx.ashley.entity
import ktx.ashley.with

class PlayerEntity(game: SpaceYque): AbstractEntity(tag = "PLAYER") {

    val instance = game.gameEngine.entity {

        with<PlayerComponent>()
        with<TransformComponent>{

            position.set(WORLD_WIDTH/2 - (size.x * 0.5f), WORLD_HEIGHT/2 - (size.y * 0.5f),0f)
        }
        with<GraphicComponent>()
        with<OrientationComponent>()
        with<MoveComponent>()
    }
}
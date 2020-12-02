package com.wisekrakrgames.spaceyque.entity

import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.entity.component.*
import ktx.ashley.entity
import ktx.ashley.with

class PlayerEntity(
        game: SpaceYque,
        var x: Float,
        var y: Float
): AbstractEntity(tag = "PLAYER") {

    val instance = game.gameEngine.entity {
        with<PlayerComponent>()
        with<TransformComponent>{
            setInitialPosition(x,y,0f)
        }
        with<GraphicComponent>()
        with<PlayerDirectionTextureComponent>()
        with<MovementComponent>()
    }
}
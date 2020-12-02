package com.wisekrakrgames.spaceyque.entity

import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.entity.component.*
import com.wisekrakrgames.spaceyque.screen.UNIT_SCALE
import ktx.ashley.entity
import ktx.ashley.with

class PlayerEntity(game: SpaceYque, var x: Float, var y: Float): AbstractEntity(tag = "PLAYER") {

    init {
        val instance = game.gameEngine.entity {
            with<PlayerComponent>()
            with<TransformComponent>{
                setInitialPosition(x,y,-1f)
            }
            with<GraphicComponent>()
            with<PlayerDirectionTextureComponent>()
            with<MovementComponent>()
        }

        game.gameEngine.entity {
            with<TransformComponent>(){size.set(0.3f, 0.6f)}
            with<AttachComponent>(){
                entity = instance
                offset.set(5.8f * UNIT_SCALE, -8f * UNIT_SCALE )
            }
            with<GraphicComponent>()
            with<AnimationComponent> {type = AnimationType.EXHAUST}

        }
    }
}
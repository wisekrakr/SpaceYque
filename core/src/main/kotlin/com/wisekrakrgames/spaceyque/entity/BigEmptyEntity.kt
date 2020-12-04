package com.wisekrakrgames.spaceyque.entity

import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.entity.component.*
import com.wisekrakrgames.spaceyque.entity.system.gameobjects.INITIAL_DAMAGE_AREA
import com.wisekrakrgames.spaceyque.screen.WORLD_WIDTH
import ktx.ashley.entity
import ktx.ashley.with

class BigEmptyEntity(game: SpaceYque): AbstractEntity(tag = "BigEmpty") {

    init {
        game.gameEngine.entity {
            with<TransformComponent> {
                size.set(WORLD_WIDTH, INITIAL_DAMAGE_AREA)
                position.z = -100f
            }
            with<GraphicComponent>()
            with<AnimationComponent>{type = AnimationType.BIG_EMPTY}
            with<BigEmptyComponent>()
        }
    }
}
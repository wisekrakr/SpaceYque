package com.wisekrakrgames.spaceyque.entity

import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.entity.component.*
import ktx.ashley.entity
import ktx.ashley.with

class EnemyEntity(game: SpaceYque, x:Float, y:Float) : AbstractEntity(tag = "Enemy"){

    init {
        val instance = game.gameEngine.entity {
            with<TransformComponent> { position.set(x, x, 0f) }
//            with<GraphicComponent>{setSpriteRegion(region)}
        }


    }
}
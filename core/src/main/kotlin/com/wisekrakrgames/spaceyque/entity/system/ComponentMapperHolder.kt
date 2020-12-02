package com.wisekrakrgames.spaceyque.entity.system

import com.badlogic.ashley.core.Entity
import com.wisekrakrgames.spaceyque.entity.component.*
import ktx.ashley.get

interface ComponentMapperHolder {

    fun getTransform(entity: Entity): TransformComponent {
        val transform = entity[TransformComponent.mapper]
        require(transform != null){"Entity must have a Transform Component: $entity"}

        return transform
    }

    fun getMovement(entity: Entity): MovementComponent {
        val move = entity[MovementComponent.mapper]
        require(move != null){"Entity must have a Graphic Component: $entity"}

        return move
    }

    fun getPlayerDirectionTextureComponent(entity: Entity): PlayerDirectionTextureComponent {
        val directionTextureComponent = entity[PlayerDirectionTextureComponent.mapper]
        require(directionTextureComponent != null){"Entity must have a Direction Component: $entity"}

        return directionTextureComponent
    }

    fun getGraphics(entity: Entity): GraphicComponent {
        val graphic = entity[GraphicComponent.mapper]
        require(graphic != null){"Entity must have a Graphic Component: $entity"}

        return graphic
    }

    fun getRemove(entity: Entity): RemoveComponent {
        val remove = entity[RemoveComponent.mapper]
        require(remove != null){"Entity must have a Remove Component: $entity"}

        return remove
    }

    fun getPlayerComponent(entity: Entity): PlayerComponent {
        val player = entity[PlayerComponent.mapper]
        require(player != null){"Entity must have a Player Component: $entity"}

        return player
    }
}
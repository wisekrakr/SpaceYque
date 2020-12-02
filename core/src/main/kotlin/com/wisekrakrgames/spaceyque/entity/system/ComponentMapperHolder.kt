package com.wisekrakrgames.spaceyque.entity.system

import com.badlogic.ashley.core.Entity
import com.wisekrakrgames.spaceyque.entity.component.*
import ktx.ashley.get

abstract class ComponentMapperHolder {

    companion object {

        fun getTransformComponent(entity: Entity): TransformComponent {
            val transform = entity[TransformComponent.mapper]
            require(transform != null) { "Entity must have a Transform Component: $entity" }

            return transform
        }

        fun getMovementComponent(entity: Entity): MovementComponent {
            val move = entity[MovementComponent.mapper]
            require(move != null) { "Entity must have a Graphic Component: $entity" }

            return move
        }

        fun getPlayerDirectionTextureComponent(entity: Entity): PlayerDirectionTextureComponent {
            val directionTextureComponent = entity[PlayerDirectionTextureComponent.mapper]
            require(directionTextureComponent != null) { "Entity must have a Direction Component: $entity" }

            return directionTextureComponent
        }

        fun getGraphicsComponent(entity: Entity): GraphicComponent {
            val graphic = entity[GraphicComponent.mapper]
            require(graphic != null) { "Entity must have a Graphic Component: $entity" }

            return graphic
        }

        fun getRemoveComponent(entity: Entity): RemoveComponent {
            val remove = entity[RemoveComponent.mapper]
            require(remove != null) { "Entity must have a Remove Component: $entity" }

            return remove
        }

        fun getPlayerComponent(entity: Entity): PlayerComponent {
            val player = entity[PlayerComponent.mapper]
            require(player != null) { "Entity must have a Player Component: $entity" }

            return player
        }

        fun getBigEmptyComponent(entity: Entity): BigEmptyComponent {
            val bigEmpty = entity[BigEmptyComponent.mapper]
            require(bigEmpty != null) { "Entity must have a Big Empty Component: $entity" }

            return bigEmpty
        }

        fun getAnimationComponent(entity: Entity): AnimationComponent {
            val animation = entity[AnimationComponent.mapper]
            require(animation != null) { "Entity must have an Animation Component: $entity" }

            return animation
        }

        fun getAttachComponent(entity: Entity): AttachComponent {
            val attach = entity[AttachComponent.mapper]
            require(attach != null) { "Entity must have an Attach Component: $entity" }

            return attach
        }
    }
}
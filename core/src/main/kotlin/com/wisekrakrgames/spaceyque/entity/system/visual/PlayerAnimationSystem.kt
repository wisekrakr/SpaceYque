package com.wisekrakrgames.spaceyque.entity.system.visual

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.wisekrakrgames.spaceyque.entity.component.GraphicComponent
import com.wisekrakrgames.spaceyque.entity.component.PlayerComponent
import com.wisekrakrgames.spaceyque.entity.component.PlayerDirectionTextureComponent
import com.wisekrakrgames.spaceyque.entity.component.PlayerTextureDirection
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerAnimationSystem(graphics: TextureAtlas) :
        IteratingSystem(allOf(PlayerComponent::class, PlayerDirectionTextureComponent::class, GraphicComponent::class).get()),
        EntityListener{

    private val defaultRegion = graphics.findRegion("player_red")
    private val leftRegion = graphics.findRegion("player_red_left")
    private val rightRegion = graphics.findRegion("player_red_right")

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(family, this)
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        engine.removeEntityListener(this)
    }

    override fun entityAdded(entity: Entity) {
        entity[GraphicComponent.mapper]?.setSpriteRegion(defaultRegion)
    }

    override fun entityRemoved(entity: Entity?) = Unit

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val direction = ComponentMapperHolder.getPlayerDirectionTextureComponent(entity)
        val graphic = ComponentMapperHolder.getGraphicsComponent(entity)

        if(direction.direction == direction.lastDirection && graphic.sprite.texture != null){
            //texture set and direction not changed, do nothing
            return
        }

        direction.lastDirection = direction.direction
        val region = when(direction.direction){
            PlayerTextureDirection.LEFT -> leftRegion
            PlayerTextureDirection.RIGHT -> rightRegion
            else -> defaultRegion
        }
        graphic.setSpriteRegion(region)

    }
}
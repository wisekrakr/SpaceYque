package com.wisekrakrgames.spaceyque.entity.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.wisekrakrgames.spaceyque.entity.component.*
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerAnimationSystem(
        graphicsRenderer: GraphicsRenderer
) : IteratingSystem(allOf(PlayerComponent::class, PlayerDirectionTextureComponent::class, GraphicComponent::class).get()),
        EntityListener, ComponentMapperHolder{

    private val defaultRegion : TextureRegion

    init {
        defaultRegion = graphicsRenderer.graphicsAtlas.findRegion("playerShip2_blue")
    }

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
        val direction = getPlayerDirectionTextureComponent(entity)
        val graphic = getGraphics(entity)

        if(direction.direction == direction.lastDirection && graphic.sprite.texture != null){
            //texture set and direction not changed, do nothing
            return
        }

//        direction.lastDirection = direction.direction
//        val region = when(direction.direction){
//            Orientation.LEFT -> leftRegion
//            Orientation.RIGHT -> rightRegion
//            else -> defaultRegion
//        }
        graphic.setSpriteRegion(defaultRegion)

    }
}
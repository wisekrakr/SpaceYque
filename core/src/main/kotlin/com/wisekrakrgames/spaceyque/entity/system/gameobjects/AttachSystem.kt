package com.wisekrakrgames.spaceyque.entity.system.gameobjects

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.IteratingSystem
import com.wisekrakrgames.spaceyque.entity.component.AttachComponent
import com.wisekrakrgames.spaceyque.entity.component.GraphicComponent
import com.wisekrakrgames.spaceyque.entity.component.RemoveComponent
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getAttachComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getGraphicsComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getTransformComponent
import ktx.ashley.addComponent
import ktx.ashley.allOf
import ktx.ashley.get

class AttachSystem:
        EntityListener,
        IteratingSystem(allOf(AttachComponent::class, TransformComponent::class, GraphicComponent::class).get()) {

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(this)
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        engine.removeEntityListener(this)
    }

    override fun entityAdded(entity: Entity) = Unit

    override fun entityRemoved(removedentity: Entity) {
        entities.forEach { entity ->
            entity[AttachComponent.mapper]?.let { attach ->
                if(attach.entity == removedentity){
                    entity.addComponent<RemoveComponent>(engine)
                }
            }
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val attach = getAttachComponent(entity)
        val graphic = getGraphicsComponent(entity)
        val transform = getTransformComponent(entity)

        //update position from attach entities
        attach.entity[TransformComponent.mapper]?.let { attachTransform ->
            transform.interpolatedPosition.set(
                    attachTransform.interpolatedPosition.x + attach.offset.x,
                    attachTransform.interpolatedPosition.y + attach.offset.y,
                    transform.position.z
            )
        }

        //update graphics alpha value for attached entities
        attach.entity[GraphicComponent.mapper]?.let { attachGraphic ->
            graphic.sprite.setAlpha(attachGraphic.sprite.color.a)
        }
    }
}
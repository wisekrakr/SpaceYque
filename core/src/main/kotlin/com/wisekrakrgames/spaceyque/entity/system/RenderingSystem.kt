package com.wisekrakrgames.spaceyque.entity.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.entity.component.GraphicComponent
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import com.wisekrakrgames.spaceyque.entity.system.ComponentMapperHolder.Companion.getAttachComponent
import com.wisekrakrgames.spaceyque.entity.system.ComponentMapperHolder.Companion.getGraphicsComponent
import com.wisekrakrgames.spaceyque.entity.system.ComponentMapperHolder.Companion.getTransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.error
import ktx.log.logger

private val LOG = logger<RenderingSystem>()


class RenderingSystem(
        private val batch: Batch,
        private val viewport: Viewport
) : SortedIteratingSystem(
        allOf(TransformComponent::class, GraphicComponent::class).get(), compareBy { entity -> entity[TransformComponent.mapper] }
) {
    override fun update(deltaTime: Float) {
        //forces an entity in front of the other if the y coord is lower
        forceSort()
        viewport.apply()
        batch.use(viewport.camera.combined) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        //We already have these components, but Ashley sometimes messes things up, so this is for extra security
        val transform = getTransformComponent(entity)
        val graphic = getGraphicsComponent(entity)

        if(graphic.sprite.texture == null){
            LOG.error { "Entity has no texture to render. Entity: $entity" }
            return
        }

        //place the sprite on top of the calculated position of the entity
        graphic.sprite.run {
            setOrigin(transform.interpolatedPosition.x + (transform.size.x * 0.5f),
                    transform.interpolatedPosition.y + (transform.size.y * 0.5f))

            setPosition(transform.interpolatedPosition.x, transform.interpolatedPosition.y)
            setSize(transform.size.x, transform.size.y)

            rotation = transform.rotation
            draw(batch)
        }

    }
}
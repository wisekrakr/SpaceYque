package com.wisekrakrgames.spaceyque.entity.system.core

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.entity.component.PlayerTextureDirection
import com.wisekrakrgames.spaceyque.entity.component.PlayerComponent
import com.wisekrakrgames.spaceyque.entity.component.PlayerDirectionTextureComponent
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getPlayerDirectionTextureComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getTransformComponent
import ktx.ashley.allOf

private const val TOUCH_TOLERANCE_DISTANCE = 0.2f

class InputSystem(
        private val worldViewport: Viewport
) : IteratingSystem(
        allOf(PlayerComponent::class, TransformComponent::class, PlayerDirectionTextureComponent::class).get()
) {
    private val inputVector = Vector2()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val direction = getPlayerDirectionTextureComponent(entity)
        val transform = getTransformComponent(entity)

        inputVector.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
        worldViewport.unproject(inputVector)

        val difference = (inputVector.x - transform.position.x) - (transform.size.x * 0.5f)

        direction.direction= when{
            difference < -TOUCH_TOLERANCE_DISTANCE -> PlayerTextureDirection.LEFT
            difference > TOUCH_TOLERANCE_DISTANCE -> PlayerTextureDirection.RIGHT
            else -> PlayerTextureDirection.DEFAULT
        }
    }

}
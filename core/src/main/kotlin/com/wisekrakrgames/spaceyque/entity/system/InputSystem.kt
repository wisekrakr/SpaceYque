package com.wisekrakrgames.spaceyque.entity.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.entity.component.Orientation
import com.wisekrakrgames.spaceyque.entity.component.OrientationComponent
import com.wisekrakrgames.spaceyque.entity.component.PlayerComponent
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import ktx.ashley.allOf

private const val TOUCH_TOLERANCE_DISTANCE = 0.2f

class InputSystem(
        private val viewport: Viewport,
) : IteratingSystem(
        allOf(PlayerComponent::class, TransformComponent::class, OrientationComponent::class).get()
) , ComponentMapperHolder
{
    private val inputVector = Vector2()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val direction = getOrientation(entity)
        val transform = getTransform(entity)

        inputVector.x = Gdx.input.x.toFloat()
        viewport.unproject(inputVector)

        val difference = (inputVector.x - transform.position.x) - (transform.size.x * 0.5f)

        direction.direction= when{
            difference < -TOUCH_TOLERANCE_DISTANCE -> Orientation.LEFT
            difference > TOUCH_TOLERANCE_DISTANCE -> Orientation.RIGHT
            else -> Orientation.DEFAULT
        }

        var mouseAiming = Math.atan2(Gdx.input.y.toDouble(), Gdx.input.x.toDouble()) - MathUtils.PI / 2;

//
//        viewport.camera.position.set(transform.position.x, transform.position.y, 100f)
//        viewport.camera.up.set(1f, 0f, 0f)
//        viewport.camera.rotate((transform.rotation * 180) / Math.PI.toFloat(), 0f, 0f, 1f)
//        viewport.camera.update()
    }
}
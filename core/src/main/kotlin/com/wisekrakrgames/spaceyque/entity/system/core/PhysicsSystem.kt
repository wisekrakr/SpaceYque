package com.wisekrakrgames.spaceyque.entity.system.core

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getTransformComponent
import ktx.ashley.allOf
import java.util.*
import kotlin.math.min

const val MAX_TIME_STEP = 1/60f

class PhysicsSystem: IteratingSystem(allOf(TransformComponent::class).get()){

    private var accumulator = 0f
    private val gameObjects: HashSet<Entity> = HashSet<Entity>()

    override fun update(deltaTime: Float) {

        val frameTime = min(deltaTime, MAX_TIME_STEP)
        accumulator += frameTime

        while (accumulator >= MAX_TIME_STEP) {
            accumulator -= MAX_TIME_STEP

            entities.forEach { entity ->
                getTransformComponent(entity).let { transform -> transform.prevPosition.set(transform.position)}
            }

            super.update(deltaTime)
        }

        //interpolate rendering position between previous position and current position
        val alpha = accumulator / MAX_TIME_STEP
        entities.forEach { entity ->
            getTransformComponent(entity).let { transform ->
                transform.interpolatedPosition.set(
                        MathUtils.lerp(transform.prevPosition.x, transform.position.x, alpha),
                        MathUtils.lerp(transform.prevPosition.y, transform.position.y, alpha),
                        transform.position.z
                )
            }
        }

        gameObjects.clear()
    }


    override fun processEntity(entity: Entity, deltaTime: Float) {
        gameObjects.add(entity)
    }

}
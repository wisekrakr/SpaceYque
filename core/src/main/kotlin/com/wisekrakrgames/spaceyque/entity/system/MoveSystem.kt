package com.wisekrakrgames.spaceyque.entity.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.wisekrakrgames.spaceyque.entity.component.*
import com.wisekrakrgames.spaceyque.screen.WORLD_HEIGHT
import com.wisekrakrgames.spaceyque.screen.WORLD_WIDTH
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.ashley.get
import kotlin.math.max
import kotlin.math.min

const val UPDATE_RATE = 1/30f


class MoveSystem:
        IteratingSystem(allOf(TransformComponent::class, MoveComponent::class).exclude(RemoveComponent::class).get()),
        ComponentMapperHolder
{

    private var accumulator = 0f

    override fun update(deltaTime: Float) {

        accumulator += deltaTime
        while (accumulator >= UPDATE_RATE){
            accumulator -= UPDATE_RATE
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = getTransform(entity)
        val move = getMoving(entity)
        val player = entity[PlayerComponent.mapper]

        if(player != null){
            entity[OrientationComponent.mapper]?.let { orientation ->
                movePlayer(transform, move, player, orientation, deltaTime)
            }
        }else{
            moveEntity(transform, move, deltaTime)
        }
    }

    private fun movePlayer(
            transform: TransformComponent,
            move: MoveComponent,
            player: PlayerComponent,
            orientation: OrientationComponent,
            deltaTime: Float)
    {
        //update horizontal speed
        move.speed.x = when(orientation.direction){
            Orientation.LEFT -> min(0f, move.speed.x - move.accelerationX * deltaTime)
            Orientation.RIGHT -> max(0f, move.speed.x + move.accelerationX * deltaTime)
            else -> 0f
        }
        move.speed.x = MathUtils.clamp(move.speed.x, -move.maxHorizontalSpeed, move.maxHorizontalSpeed)

        //update vertical speed
        move.speed.y = MathUtils.clamp(
                move.speed.y - move.accelerationY * deltaTime,
                -move.maxNegativeSpeed, move.maxPositiveSpeed
        )
        moveEntity(transform, move, deltaTime)

    }

    private fun moveEntity(transform: TransformComponent, move: MoveComponent, deltaTime: Float) {
        transform.position.x = MathUtils.clamp(
                transform.position.x + move.speed.x * deltaTime,
                0f,
                WORLD_WIDTH - transform.size.x
        )
        transform.position.y = MathUtils.clamp(
                transform.position.y + move.speed.y * deltaTime,
                1f,
                WORLD_HEIGHT + 1f - transform.size.y
        )
    }


}
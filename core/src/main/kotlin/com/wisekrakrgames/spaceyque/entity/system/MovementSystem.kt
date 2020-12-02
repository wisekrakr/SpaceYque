package com.wisekrakrgames.spaceyque.entity.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.entity.component.*
import com.wisekrakrgames.spaceyque.entity.system.ComponentMapperHolder.Companion.getMovementComponent
import com.wisekrakrgames.spaceyque.entity.system.ComponentMapperHolder.Companion.getTransformComponent
import com.wisekrakrgames.spaceyque.screen.WORLD_HEIGHT
import com.wisekrakrgames.spaceyque.screen.WORLD_WIDTH
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.ashley.get
import ktx.log.logger
import kotlin.math.*


private val LOG = logger<MovementSystem>()

private const val VER_ACCELERATION = 2.25f
private const val HOR_ACCELERATION = 16.5f
private const val MAX_VER_NEG_PLAYER_SPEED = 0.75f
private const val MAX_VER_POS_PLAYER_SPEED = 5f
private const val MAX_HOR_SPEED = 5.5f

class MovementSystem(
        private val viewport: Viewport,
):IteratingSystem(allOf(TransformComponent::class, MovementComponent::class).exclude(RemoveComponent::class).get()){

    private val isFreeRoamGame = false

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = getTransformComponent(entity)
        val move = getMovementComponent(entity)

        if(isFreeRoamGame) freeRoamPlayerMovement(transform, move, deltaTime)

        val player = entity[PlayerComponent.mapper]
        if (player != null) {
            entity[PlayerDirectionTextureComponent.mapper]?.let { direction ->
                movePlayer(transform, move, player, direction, deltaTime)
                freeRoamPlayerMovement(transform, move, deltaTime)
            }
        } else {
            moveEntity(transform, move, deltaTime)
        }
    }

    /**
     * This part of movement code is all for the arcade part of the game
     */
    private fun movePlayer(
            transform: TransformComponent,
            move: MovementComponent,
            player: PlayerComponent,
            direction: PlayerDirectionTextureComponent,
            deltaTime: Float
    ) {
        // update horizontal move speed
        move.velocity.x = when (direction.direction) {
            PlayerTextureDirection.LEFT -> min(0f, move.velocity.x - HOR_ACCELERATION * deltaTime)
            PlayerTextureDirection.RIGHT -> max(0f, move.velocity.x + HOR_ACCELERATION * deltaTime)
            else -> 0f
        }
        move.velocity.x = MathUtils.clamp(move.velocity.x, -MAX_HOR_SPEED, MAX_HOR_SPEED)

        // update vertical move speed
        move.velocity.y = MathUtils.clamp(
                move.velocity.y - VER_ACCELERATION * deltaTime,
                -MAX_VER_NEG_PLAYER_SPEED,
                MAX_VER_POS_PLAYER_SPEED
        )

        // move player and update distance travelled so far
        val oldY = transform.position.y

//        player.distance += abs(transform.position.y - oldY)
//        gameEventManager.dispatchEvent(GameEvent.PlayerMove.apply {
//            distance = player.distance
//            speed = move.speed.y
//        })

        moveEntity(transform, move, deltaTime)
    }

    private fun moveEntity(
            transform: TransformComponent,
            move: MovementComponent,
            deltaTime: Float
    ) {
        transform.position.x = MathUtils.clamp(
                transform.position.x + move.velocity.x * deltaTime,
                0f,
                WORLD_WIDTH - transform.size.x
        )
        transform.position.y = MathUtils.clamp(
                transform.position.y + move.velocity.y * deltaTime,
                1f,
                WORLD_HEIGHT + 1f - transform.size.y
        )
    }

    /** End of the arcade part */

    /**
     * This part of movement code is all for the free roam part of the game. Still is progress TODO PLAYER ROTATION + STICKY CAMERA
     */
    private fun freeRoamPlayerMovement(transform: TransformComponent, movement: MovementComponent, deltaTime: Float){
        val mousePos = Vector3(Gdx.input.x - (transform.size.x * 0.5f), Gdx.input.y.toFloat(), 0f)
        viewport.camera.unproject(mousePos)

        movement.angle = TransformComponent.angleBetween(
                Vector2(transform.position.x, transform.position.y), Vector2(mousePos.x, mousePos.y)).toDouble()

        transform.apply {
            position.x += (movement.velocity.x + movement.acceleration * cos(movement.angle)).toFloat() * deltaTime
            position.y += (movement.velocity.y + movement.acceleration * sin(movement.angle)).toFloat() * deltaTime
            //rotation = movement.angle.toFloat() * MathUtils.PI /2 * deltaTime
        }
        //cameraMovement(transform, movement)
    }

    private fun cameraMovement(transform: TransformComponent, movement: MovementComponent) {

        val camAngle = -getCameraCurrentXYAngle(viewport.camera) + 180;

        viewport.camera.position.set(
                transform.interpolatedPosition.x + (transform.size.x * 0.5f),
                transform.interpolatedPosition.y + (transform.size.y * 0.5f),
                100f
        )

//        viewport.camera.rotate((transform.rotation * 180 / Math.PI).toFloat(), 0f, 0f, 1f)
        //viewport.camera.rotate((camAngle-movement.angle).toFloat() + 180,0f,0f,1f)
        viewport.camera.update()
    }
    /** End of Free Roam Movement Code */

    private fun getCameraCurrentXYAngle(cam: Camera): Float {
        return atan2(cam.up.x.toDouble(), cam.up.y.toDouble()).toFloat() * MathUtils.radiansToDegrees
    }
}
package com.wisekrakrgames.spaceyque.entity.system.core

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.wisekrakrgames.spaceyque.entity.component.PlayerComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getPlayerComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getTransformComponent
import ktx.ashley.allOf
import ktx.ashley.getSystem
import ktx.log.debug
import ktx.log.logger
import kotlin.math.max
import kotlin.math.min

private val LOG = logger<DebugSystem>()
private const val DEBUG_UPDATE_RATE = 0.5f

class DebugSystem(
        private var camera: Camera
) : IntervalIteratingSystem(allOf(PlayerComponent::class).get(), DEBUG_UPDATE_RATE) {

    private val shapeRenderer: ShapeRenderer = ShapeRenderer()

    init {
        setProcessing(true)
        shapeRenderer.setAutoShapeType(true)
    }

    override fun processEntity(entity: Entity) {
        val transform = getTransformComponent(entity)
        val player = getPlayerComponent(entity)

        shapeRenderer.begin()
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.color = Color.GOLD
        shapeRenderer.circle(transform.interpolatedPosition.x + (transform.size.x * 0.5f),
                transform.interpolatedPosition.y + (transform.size.y * 0.5f), 0.5f)
        shapeRenderer.end()

        when{
            //reset player
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)->{
                transform.setInitialPosition(0f,0f,0f)
                player.health = 1f
                player.shield = 0f
                LOG.debug { "RESET PLAYER" }

            }
            //kill player
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)->{
                transform.position.y = 1f
                LOG.debug { "KILL PLAYER" }
            }
            //add shield to player
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)->{
                player.shield = min(player.maxShield, player.shield + 25f)
                LOG.debug { "ADD SHIELD TO PLAYER" }
            }
            //remove shield from player
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)->{
                player.shield = max(0f, player.shield - 25f)
                LOG.debug { "REMOVE SHIELD TO PLAYER" }
            }
            //disable movement
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)->{
                engine.getSystem<MovementSystem>().setProcessing(false)
                LOG.debug { "STOP EVERYTHING" }
            }
            //enable movement
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)->{
                engine.getSystem<MovementSystem>().setProcessing(true)
            }
            // trigger player damage event
//            Gdx.input.isKeyPressed(Input.Keys.NUM_6) -> {
//                player.health = max(1f, player.health - PLAYER_DAMAGE)
//                gameEventManager.dispatchEvent(GameEvent.PlayerHit.apply {
//                    this.player = entity
//                    life = player.life
//                    maxLife = player.maxLife
//                })
//            }
            // trigger player heal event
//            Gdx.input.isKeyPressed(Input.Keys.NUM_7) -> {
//                engine.getSystem<PowerUpSystem>()
//                        .spawnPowerUp(PowerUpType.LIFE, transform.position.x, transform.position.y)
//            }
            // play three random sounds
//            Gdx.input.isKeyPressed(Input.Keys.NUM_8) -> {
//                repeat(NUM_SOUNDS_TO_TEST) {
//                    audioService.play(SoundAsset.values()[MathUtils.random(0, SoundAsset.values().size - 1)])
//                }
//            }
        }

        Gdx.graphics.setTitle(
                "SYQ DEBUG - pos:${transform.position},\n rot:${transform.rotation},\nlife:${player.health},\n shield:${player.shield}.\n distance:${player.distance}"
        )
    }
}
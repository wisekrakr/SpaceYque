package com.wisekrakrgames.spaceyque.entity.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.wisekrakrgames.spaceyque.entity.component.PlayerComponent
import ktx.ashley.allOf
import ktx.ashley.getSystem
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

private const val DEBUG_UPDATE_RATE = 0.5f

class DebugSystem(
        var camera: Camera
) : IntervalIteratingSystem(allOf(PlayerComponent::class).get(), DEBUG_UPDATE_RATE), ComponentMapperHolder {

    private val shapeRenderer: ShapeRenderer = ShapeRenderer()

    init {
        setProcessing(true)
        shapeRenderer.setAutoShapeType(true)
    }

    override fun processEntity(entity: Entity) {
        val transform = getTransform(entity)
        val player = getPlayerComponent(entity)

        shapeRenderer.begin()
        shapeRenderer.projectionMatrix = camera.combined;
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
            }
            //kill player
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)->{
                transform.position.y = 1f
                player.health = 1f
                player.shield = 0f
            }
            //add shield to player
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)->{
                player.shield = min(player.maxShield, player.shield + 25f)
            }
            //remove shield from player
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)->{
                player.shield = max(0f, player.shield - 25f)
            }
            //disable movement
            Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)->{
                engine.getSystem<MovementSystem>().setProcessing(false)
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
                "SYQ DEBUG - pos:${transform.position}, rot:${transform.rotation}, life:${player.health}, shield:${player.shield}"
        )
    }
}
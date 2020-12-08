package com.wisekrakrgames.spaceyque.entity.system.gameobjects

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.wisekrakrgames.spaceyque.audiovisual.audio.AudioService
import com.wisekrakrgames.spaceyque.entity.component.*
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder
import com.wisekrakrgames.spaceyque.event.GameEvent
import com.wisekrakrgames.spaceyque.event.GameEventManager
import com.wisekrakrgames.spaceyque.audiovisual.screen.WORLD_HEIGHT
import com.wisekrakrgames.spaceyque.audiovisual.screen.WORLD_WIDTH
import ktx.ashley.*
import ktx.collections.GdxArray
import ktx.collections.gdxArrayOf
import kotlin.math.min

private const val MAX_SPAWN_INTERVAL = 2.5f
private const val MIN_SPAWN_INTERVAL = 0.5f

private class SpawnPattern(
    type1: PowerUpType = PowerUpType.NONE,
    type2: PowerUpType = PowerUpType.NONE,
    type3: PowerUpType = PowerUpType.NONE,
    type4: PowerUpType = PowerUpType.NONE,
    type5: PowerUpType = PowerUpType.NONE,
    val types: GdxArray<PowerUpType> = gdxArrayOf(type1, type2, type3, type4, type5)
)

class PowerUpSystem(private val gameEventManager: GameEventManager, private val audioService: AudioService) :
        IteratingSystem(allOf(PowerUpComponent::class, TransformComponent::class).exclude(RemoveComponent::class).get()) {

    private val playerRectangle = Rectangle()
    private val powerUpRectangle = Rectangle()
    private val playerEntities by lazy {
        engine.getEntitiesFor(
                allOf(PlayerComponent::class).exclude(RemoveComponent::class).get()
        )
    }

    private var spawnTime = 0f;
    private val spawnPatterns = gdxArrayOf(
            SpawnPattern(type1 = PowerUpType.SHORT_BOOST, type2 = PowerUpType.LONG_BOOST, type5 = PowerUpType.HEALTH),
            SpawnPattern(type2 = PowerUpType.HEALTH, type3 = PowerUpType.SHIELD, type4 = PowerUpType.LONG_BOOST),
            SpawnPattern(type1 = PowerUpType.SHIELD, type4 = PowerUpType.SHORT_BOOST, type5 = PowerUpType.HEALTH),
            SpawnPattern(type2 = PowerUpType.LONG_BOOST, type3 = PowerUpType.HEALTH, type4 = PowerUpType.SHORT_BOOST),
    )
    private val currentSpawnPattern = GdxArray<PowerUpType>()

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        spawnTime -= deltaTime

        if(spawnTime <= 0){
            spawnTime = MathUtils.random(MIN_SPAWN_INTERVAL, MAX_SPAWN_INTERVAL)

            if(currentSpawnPattern.isEmpty){
                currentSpawnPattern.addAll(spawnPatterns[MathUtils.random(0, spawnPatterns.size - 1)].types)
            }

            val powerUpType = currentSpawnPattern.removeIndex(0)

            if(powerUpType == PowerUpType.NONE){
                return
            }

            spawnPowerUp(powerUpType, 1f * MathUtils.random(0, WORLD_WIDTH.toInt() - 1), WORLD_HEIGHT, MathUtils.random(-5f,-10f))
        }
    }

    private fun spawnPowerUp(powerUpType: PowerUpType, x: Float, y: Float, speed : Float) {
        engine.entity {
            with<TransformComponent>(){
                setInitialPosition(x,y,0f)
                size.set(0.7f,0.7f)
            }
            with<PowerUpComponent>(){type = powerUpType}
            with<AnimationComponent>(){type = powerUpType.animationType}
            with<GraphicComponent>()
            with<MovementComponent>(){velocity.y = speed}
         }

    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = ComponentMapperHolder.getTransformComponent(entity)

        if(transform.position.y <= 1f){
            entity.addComponent<RemoveComponent>(engine)
            return
        }

        powerUpRectangle.set(transform.position.x, transform.position.y, transform.size.x, transform.size.y)
        playerEntities.forEach { player ->
            player[TransformComponent.mapper]?.let { playerTransform ->
                playerRectangle.set(
                        playerTransform.position.x,
                        playerTransform.position.y,
                        playerTransform.size.x,
                        playerTransform.size.x,
                )

                if(playerRectangle.overlaps(powerUpRectangle)){
                    collectPowerUp(player, entity)
                }
            }
        }

    }

    /**
     * When overlapping with a power up entity, this function gets called
     * We get the power up component for the power up entity and get it power up type
     * Finally we place all power up type attributes where they are needed/ we apply the power up on the player entity
     * and remove the power up.
     */
    private fun collectPowerUp(player: Entity, powerUp: Entity) {
        val powerUpComponent = ComponentMapperHolder.getPowerUpComponent(powerUp)

        powerUpComponent.type.also { powerUpType ->

            // type is SPEED BOOST
            player[MovementComponent.mapper]?.let { it.velocity.y += powerUpType.yVelocityGain }
            // type is HEALTH or SHIELD gain
            player[PlayerComponent.mapper]?.let {
                it.health = min(it.maxHealth, it.health + powerUpType.healthGain)
                it.shield = min(it.maxShield, it.shield + powerUpType.shieldGain)
            }
            // play sound effect
            audioService.play(powerUpType.soundAsset)

            // dispatch event
            gameEventManager.dispatchEvent(GameEvent.PowerUp.apply {
                this.player = player
                this.type = powerUpType
            })
        }
        // remove power up
        powerUp.addComponent<RemoveComponent>(engine)
    }
}


package com.wisekrakrgames.spaceyque.entity.system.core

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.wisekrakrgames.spaceyque.entity.component.PlayerComponent
import com.wisekrakrgames.spaceyque.entity.component.RemoveComponent
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getPlayerComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getTransformComponent
import com.wisekrakrgames.spaceyque.entity.system.gameobjects.AbstractBigEmpty
import com.wisekrakrgames.spaceyque.event.GameEvent
import com.wisekrakrgames.spaceyque.event.GameEventManager
import ktx.ashley.addComponent
import ktx.ashley.allOf
import kotlin.math.max


private const val DAMAGE_PER_SECOND = 10f
private const val DEATH_EXPLOSION_DURATION = 0.9f

/**
 * System that damages the player when the player gets hit by The Big Empty
 * This is NOT for free roam gameplay, bit for ARCADE gameplay TODO on and off switch for this system
 */
class DamageSystem(
        private val gameEventManager: GameEventManager
) : IteratingSystem(allOf(PlayerComponent::class, TransformComponent::class).exclude(RemoveComponent::class.java).get()){

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = getTransformComponent(entity)
        val playerComponent = getPlayerComponent(entity)

        //damage the player if it is in the damage area
        if(transform.position.y <= AbstractBigEmpty.damageArea){
            var damage = DAMAGE_PER_SECOND * deltaTime

            //when the player has a shield
            if(playerComponent.shield > 0) {
                val blockAmount = playerComponent.shield
                playerComponent.shield = max(0f, playerComponent.shield - damage)
                damage -= blockAmount

                //entire damage was blocked
                if(damage <= 0)
                    return
            }

            playerComponent.health -= damage
            gameEventManager.dispatchEvent(GameEvent.PlayerHit.apply {
                this.player = entity
                health = playerComponent.health
                maxHealth = playerComponent.maxHealth
            })
        }

        //if the player has no more health, the player entity adds a remove component to itself and after a delay,
        //the engine will remove the entity
        if(playerComponent.health <= 0){
            gameEventManager.dispatchEvent(GameEvent.PlayerDeath.apply {
                this.distance = playerComponent.distance
            })

            entity.addComponent<RemoveComponent>(engine){
                delay = DEATH_EXPLOSION_DURATION
            }
        }
    }

}
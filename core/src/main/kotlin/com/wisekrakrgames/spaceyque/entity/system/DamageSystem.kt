package com.wisekrakrgames.spaceyque.entity.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.wisekrakrgames.spaceyque.entity.component.BigEmptyComponent
import com.wisekrakrgames.spaceyque.entity.component.PlayerComponent
import com.wisekrakrgames.spaceyque.entity.component.RemoveComponent
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import com.wisekrakrgames.spaceyque.entity.system.ComponentMapperHolder.Companion.getBigEmptyComponent
import com.wisekrakrgames.spaceyque.entity.system.ComponentMapperHolder.Companion.getPlayerComponent
import com.wisekrakrgames.spaceyque.entity.system.ComponentMapperHolder.Companion.getTransformComponent
import com.wisekrakrgames.spaceyque.screen.WORLD_HEIGHT
import ktx.ashley.addComponent
import ktx.ashley.allOf
import ktx.log.debug
import ktx.log.logger
import kotlin.math.max

private val LOG = logger<DamageSystem>()

const val DAMAGE_AREA = 2f
private const val DAMAGE_AREA_GROW_TIME = 5f
private const val DAMAGE_PER_SECOND = 25f
private const val DEATH_EXPLOSION_DURATION = 0.9f

/**
 * System that damages the player when the player gets hit by The Big Empty (area at the bottom of the screen)
 * This is NOT for free roam gameplay, bit for ARCADE gameplay TODO on and off switch for this system
 */
class DamageSystem : IteratingSystem(allOf(PlayerComponent::class, TransformComponent::class).exclude(RemoveComponent::class.java).get()){

    private var increaseDamageAreaInterval = 0f

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = getTransformComponent(entity)
        val playerComponent = getPlayerComponent(entity)

        chaseMechanismForTheBigEmpty(transform, playerComponent,  deltaTime)

        //if the player has no more health, the player entity adds a remove component to itself and after a delay,
        //the engine will remove the entity
        if(playerComponent.health <= 0){
            entity.addComponent<RemoveComponent>(engine){
                delay = DEATH_EXPLOSION_DURATION
            }
        }
    }

    private fun chaseMechanismForTheBigEmpty(
            transform: TransformComponent,
            playerComponent: PlayerComponent,
            deltaTime: Float
    ){
        increaseDamageAreaInterval += deltaTime

//        if(increaseDamageAreaInterval >= DAMAGE_AREA_GROW_TIME){
//            damageArea++
//
//            if(bigEmptyComponent.damageArea >= WORLD_HEIGHT/2){
//                bigEmptyComponent.damageArea--
//            }
//
//            increaseDamageAreaInterval = 0f
//        }
//
//        LOG.debug { " \n time for a change in $increaseDamageAreaInterval \n $bigEmptyComponent.damageArea size of the damage area" }

        //damage the player if it is in the damage area
        if(transform.position.y <=  2f){
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
        }


    }
}
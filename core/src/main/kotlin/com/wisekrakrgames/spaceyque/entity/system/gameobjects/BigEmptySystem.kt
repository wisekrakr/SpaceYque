package com.wisekrakrgames.spaceyque.entity.system.gameobjects

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.wisekrakrgames.spaceyque.entity.component.BigEmptyComponent
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder
import com.wisekrakrgames.spaceyque.screen.WORLD_HEIGHT
import ktx.ashley.allOf
import ktx.log.logger

private val LOG = logger<BigEmptySystem>()

const val INITIAL_DAMAGE_AREA = 0.3f
private const val DAMAGE_GROW_TICKER = 0.001f

class BigEmptySystem : IteratingSystem(allOf(TransformComponent::class, BigEmptyComponent::class).get()){

    private var isGrowing: Boolean = true

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = ComponentMapperHolder.getTransformComponent(entity)
        val bigEmptyComponent = ComponentMapperHolder.getBigEmptyComponent(entity)

        if(isGrowing)growDamageArea(bigEmptyComponent)

        if(bigEmptyComponent.damageArea >= WORLD_HEIGHT/2 + 1f){
            isGrowing = false
            bigEmptyComponent.damageArea -= DAMAGE_GROW_TICKER

            if(transform.position.y == INITIAL_DAMAGE_AREA){
                isGrowing = true
            }

        }
        transform.size.y = bigEmptyComponent.damageArea
        AbstractBigEmpty.damageArea = transform.size.y

        //LOG.debug { " \n DAMAGE Y POS $damageAreaYPos \n ${bigEmptyComponent.damageArea} size of the damage area" }

    }

    private fun growDamageArea(bigEmptyComponent: BigEmptyComponent){
        bigEmptyComponent.damageArea += DAMAGE_GROW_TICKER
    }
}

abstract class AbstractBigEmpty{
    companion object{
        var damageArea:Float = 0f
    }
}
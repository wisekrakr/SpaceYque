package com.wisekrakrgames.spaceyque.entity.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.wisekrakrgames.spaceyque.entity.component.RemoveComponent
import ktx.ashley.allOf

class RemoveSystem: IteratingSystem(allOf(RemoveComponent::class).get()) , ComponentMapperHolder{
    override fun processEntity(entity: Entity, deltaTime: Float) {

        val remove = getRemove(entity)

        remove.delay -= deltaTime
        if(remove.delay <= 0f){
            engine.removeEntity(entity)
        }

    }
}
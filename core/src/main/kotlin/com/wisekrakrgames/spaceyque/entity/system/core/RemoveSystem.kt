package com.wisekrakrgames.spaceyque.entity.system.core

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.wisekrakrgames.spaceyque.entity.component.RemoveComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getRemoveComponent
import ktx.ashley.allOf

class RemoveSystem: IteratingSystem(allOf(RemoveComponent::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {

        val remove = getRemoveComponent(entity)

        remove.delay -= deltaTime
        if(remove.delay <= 0f){
            engine.removeEntity(entity)
        }

    }
}
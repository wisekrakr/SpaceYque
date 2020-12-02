package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor



class PlayerComponent: AbstractPlayerComponent(), Component, Pool.Poolable {


    override fun reset() {
        health = MAX_HEALTH
        maxHealth = MAX_HEALTH
        shield = 0f
        maxShield = MAX_SHIELD
        distance = 0f

    }

    companion object{
        val mapper = mapperFor<PlayerComponent>()
    }
}
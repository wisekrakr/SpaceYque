package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor



class PlayerComponent: Component, Pool.Poolable {
    var health = MAX_HEALTH
    var maxHealth= MAX_HEALTH
    var shield= 0f
    var maxShield= MAX_HEALTH
    var distance= 0f

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
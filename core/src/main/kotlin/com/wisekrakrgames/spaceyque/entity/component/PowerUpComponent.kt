package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

enum class PowerUpType(
        val animationType: AnimationType,
        val healthGain: Float = 0f,
        val shieldGain: Float = 0f,
        val yVelocityGain: Float = 0f,
){
    NONE(AnimationType.NONE),
    SHORT_BOOST(AnimationType.SHORT_BOOST, yVelocityGain = 3f),
    LONG_BOOST(AnimationType.LONG_BOOST, yVelocityGain = 3.75f),
    HEALTH(AnimationType.HEALTH, healthGain = 20f),
    SHIELD(AnimationType.SHIELD, shieldGain = 25f),
}

class PowerUpComponent : Component, Pool.Poolable {
    var type = PowerUpType.NONE

    override fun reset() {
        type = PowerUpType.NONE
    }

    companion object{
        val mapper = mapperFor<PowerUpComponent>()
    }
}
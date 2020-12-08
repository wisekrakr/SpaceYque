package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.utils.Pool
import com.wisekrakrgames.spaceyque.audiovisual.SoundAsset
import ktx.ashley.mapperFor

enum class PowerUpType(
        val animationType: AnimationType,
        val healthGain: Float = 0f,
        val shieldGain: Float = 0f,
        val yVelocityGain: Float = 0f,
        val soundAsset: SoundAsset,
){
    NONE(AnimationType.NONE, soundAsset = SoundAsset.BLOCK),
    SHORT_BOOST(AnimationType.SHORT_BOOST, yVelocityGain = 3f, soundAsset = SoundAsset.SHORT_BOOST),
    LONG_BOOST(AnimationType.LONG_BOOST, yVelocityGain = 4f, soundAsset = SoundAsset.LONG_BOOST),
    HEALTH(AnimationType.HEALTH, healthGain = 20f, soundAsset = SoundAsset.HEALTH),
    SHIELD(AnimationType.SHIELD, shieldGain = 25f, soundAsset = SoundAsset.SHIELD),
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
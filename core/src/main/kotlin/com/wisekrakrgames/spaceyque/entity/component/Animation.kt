package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import ktx.collections.GdxArray

private const val FRAME_DURATION = 1/20f
private const val SPEED_RATE = 1f

enum class AnimationType(
        val atlasKey: String,
        val playMode: Animation.PlayMode = Animation.PlayMode.LOOP,
        val speedRate: Float = SPEED_RATE
){
    NONE(""),
    BIG_EMPTY("frame"),
    EXHAUST("laserRedEx"),
    SHORT_BOOST("shortboost", speedRate = 0.2f),
    LONG_BOOST("longboost", speedRate = 0.2f),
    HEALTH("health", speedRate = 0.2f),
    SHIELD("shield_gold", speedRate = 0.2f),

}

class Animation2D(
        val type: AnimationType,
        keyFrames: GdxArray<out TextureRegion>,
        playMode: PlayMode = PlayMode.LOOP,
        speedRate: Float = SPEED_RATE
): Animation<TextureRegion>(FRAME_DURATION / speedRate, keyFrames, playMode)

class AnimationComponent: Component, Pool.Poolable {

    var type = AnimationType.NONE
    var stateTime = 0f

    lateinit var animation: Animation2D

    override fun reset() {
        type = AnimationType.NONE
    }

    companion object{
        val mapper = mapperFor<AnimationComponent>()
    }

}

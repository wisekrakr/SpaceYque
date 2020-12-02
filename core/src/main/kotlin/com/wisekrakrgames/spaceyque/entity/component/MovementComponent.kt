package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor


class MovementComponent: Component, Pool.Poolable {
    var isMoving = false
    var velocity = Vector2()
    var speed = 0.0
    var acceleration = 8.0f
    var angle = 0.0

    override fun reset() {
        isMoving = false
        velocity.set(0f,0f)
        angle = 0.0
        speed = 0.0
    }

    companion object{
        val mapper = mapperFor<MovementComponent>()
    }
}


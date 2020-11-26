package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor


class MoveComponent: Component, Pool.Poolable {
    val speed = Vector2()

    var accelerationX = 16.5f
    var accelerationY = 2.25f
    var maxNegativeSpeed = 0.75f
    var maxPositiveSpeed = 5f
    var maxHorizontalSpeed = 5.5f

    override fun reset() {
        speed.set(0f,0f)
    }

    companion object{
        val mapper = mapperFor<MoveComponent>()
    }
}
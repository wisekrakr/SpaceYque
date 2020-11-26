package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class OrientationComponent: Component, Pool.Poolable {
    var direction = Orientation.DEFAULT
    var lastDirection = Orientation.DEFAULT

    override fun reset() {
        direction = Orientation.DEFAULT
        lastDirection = Orientation.DEFAULT
    }

    companion object {
        var mapper = mapperFor<OrientationComponent>()
    }
}

enum class Orientation{
    LEFT, DEFAULT, RIGHT
}
package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class RemoveComponent: Component, Pool.Poolable {

    var delay = 0f

    override fun reset() {
        delay = 0f
    }

    companion object{
        var mapper = mapperFor<RemoveComponent>()
    }
}
package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

/**
 * You add this component to an entity when you want to remove it from screen
 * It is optional to give it a delay
 */
class RemoveComponent: Component, Pool.Poolable {

    var delay = 0f

    override fun reset() {
        delay = 0f
    }

    companion object{
        var mapper = mapperFor<RemoveComponent>()
    }
}
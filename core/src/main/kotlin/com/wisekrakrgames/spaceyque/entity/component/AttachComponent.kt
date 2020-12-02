package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class AttachComponent : Component, Pool.Poolable{

    lateinit var entity: Entity
    var offset = Vector2()

    override fun reset() {
        offset.set(0f,0f)
    }

    companion object{
        val mapper = mapperFor<AttachComponent>()
    }
}
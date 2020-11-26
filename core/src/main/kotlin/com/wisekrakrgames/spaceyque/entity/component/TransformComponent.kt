package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class TransformComponent : Component, Pool.Poolable, Comparable<TransformComponent>{
    val position = Vector3()
    val size = Vector2(1f,1f)
    var rotation = 0f

    override fun reset() {
        position.set(Vector3.Zero)
        size.set(1f,1f)
        rotation = 0f
    }

    override fun compareTo(other: TransformComponent): Int {
        //if the y coord are lower for the entity, it will show itself in front of the other
        val zDiff = other.position.z.compareTo(position.z)
        return if(zDiff==0)  other.position.y.compareTo(position.y) else zDiff
    }

    companion object{
        val mapper = mapperFor<TransformComponent>()
    }
}


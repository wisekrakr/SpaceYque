package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import kotlin.math.atan2

class TransformComponent : Component, Pool.Poolable, Comparable<TransformComponent>{
    val position = Vector3()
    val prevPosition = Vector3()
    val interpolatedPosition = Vector3()
    val size = Vector2(1f,1f)
    var rotation = 0f
    var orientation = 0f

    override fun reset() {
        position.set(Vector3.Zero)
        prevPosition.set(Vector3.Zero)
        interpolatedPosition.set(Vector3.Zero)
        size.set(1f,1f)
        rotation = 0f
        orientation = 0f
    }

    fun setInitialPosition(x: Float, y: Float, z: Float){
        position.set(x, y, z)
        prevPosition.set(x, y, z)
        interpolatedPosition.set(x, y, z)
    }

    override fun compareTo(other: TransformComponent): Int {
        //if the y coord are lower for the entity, it will show itself in front of the other
        val zDiff = other.position.z.compareTo(position.z)
        return if(zDiff==0)  other.position.y.compareTo(position.y) else zDiff
    }

    companion object{
        val mapper = mapperFor<TransformComponent>()

        fun angleBetween(subject: Vector2, target: Vector2): Float {
            val attackDistanceX = target.x - subject.x
            val attackDistanceY = target.y - subject.y
            return atan2(attackDistanceY.toDouble(), attackDistanceX.toDouble()).toFloat()
        }
    }
}


package com.wisekrakrgames.spaceyque.utils

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

class Conversions {

    companion object{
        fun convert(vector: Vector3): Vector2 {
            return Vector2(vector.x, vector.y)
        }

        fun convert(vector: Vector3, z: Float): Vector3 {
            return Vector3(convert(vector), z)
        }
    }

}
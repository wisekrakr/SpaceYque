package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import com.wisekrakrgames.spaceyque.entity.system.gameobjects.INITIAL_DAMAGE_AREA
import ktx.ashley.mapperFor

class BigEmptyComponent: Component, Pool.Poolable {

    var damageArea: Float = INITIAL_DAMAGE_AREA

    override fun reset() {
        damageArea = INITIAL_DAMAGE_AREA
    }


    companion object{
        val mapper = mapperFor<BigEmptyComponent>()
    }
}
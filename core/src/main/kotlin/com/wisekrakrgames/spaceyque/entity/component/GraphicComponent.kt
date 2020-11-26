package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class GraphicComponent: Component, Pool.Poolable {

    val sprite = Sprite()

    override fun reset() {
        sprite.texture = null
        sprite.setColor(1f,1f,1f,1f)
    }

    fun setSpriteRegion(region: TextureRegion){
        sprite.run {
            setRegion(region)
            setSize(texture.width * com.wisekrakrgames.spaceyque.screen.UNIT_SCALE, texture.height * com.wisekrakrgames.spaceyque.screen.UNIT_SCALE)
            setOriginCenter()
        }
    }

    companion object{
        val mapper = mapperFor<GraphicComponent>()
    }
}
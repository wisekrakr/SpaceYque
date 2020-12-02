package com.wisekrakrgames.spaceyque.entity.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PlayerDirectionTextureComponent: Component, Pool.Poolable {
    var direction = PlayerTextureDirection.DEFAULT
    var lastDirection = PlayerTextureDirection.DEFAULT

    override fun reset() {
        direction = PlayerTextureDirection.DEFAULT
        lastDirection = PlayerTextureDirection.DEFAULT
    }

    companion object {
        var mapper = mapperFor<PlayerDirectionTextureComponent>()
    }
}

enum class PlayerTextureDirection{
    LEFT, DEFAULT, RIGHT
}
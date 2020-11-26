package com.wisekrakrgames.spaceyque.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<GraphicsRenderer>()

class GraphicsRenderer : SpriteBatch(){

    val graphicsAtlas by lazy { TextureAtlas(Gdx.files.internal("graphics/graphics.atlas")) }

    fun init(){
        setAllGraphics()
    }

    private fun setAllGraphics(){

    }

    override fun dispose() {
        super.dispose()
        graphicsAtlas.dispose()

        LOG.debug { "Sprites in batch: ${this.maxSpritesInBatch}" }
    }
}



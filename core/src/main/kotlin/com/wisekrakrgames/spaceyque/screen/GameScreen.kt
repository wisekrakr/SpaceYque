package com.wisekrakrgames.spaceyque.screen

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.wisekrakrgames.spaceyque.SpaceYque
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<GameScreen>()

class GameScreen(game: SpaceYque) : AbstractScreen(game){

    override fun show() {
        LOG.debug { "Game Screen is ON!" }
    }

    override fun render(delta: Float) {
        graphicsRenderer.renderCalls = 0
        gameEngine.update(delta)
    }


    override fun dispose() {
        graphicsRenderer.dispose()
    }
}
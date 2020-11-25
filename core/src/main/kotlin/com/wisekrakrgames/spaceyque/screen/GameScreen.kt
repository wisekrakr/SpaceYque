package com.wisekrakrgames.spaceyque.screen

import com.badlogic.gdx.utils.viewport.FitViewport
import com.wisekrakrgames.spaceyque.SpaceYque
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<GameScreen>()

class GameScreen(game: SpaceYque) : AbstractScreen(game){
    private val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)

    override fun show() {
        LOG.debug { "Game Screen is ON!" }
    }

    override fun render(delta: Float) {
        game.gameEngine.update(delta)

        viewport.apply()
        graphicsRenderer.renderAllGraphics(viewport, game.spaceEngine)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun dispose() {

        graphicsRenderer.dispose()
    }
}
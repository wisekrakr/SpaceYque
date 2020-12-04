package com.wisekrakrgames.spaceyque.screen

import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.entity.system.core.MAX_TIME_STEP
import ktx.log.debug
import ktx.log.logger
import kotlin.math.min

private val LOG = logger<GameScreen>()

class GameScreen(game: SpaceYque) : AbstractScreen(game){

    override fun show() {
        LOG.debug { "Game Screen is ON!" }

    }

    override fun render(delta: Float) {
        graphicsRenderer.renderCalls = 0
        gameEngine.update(min(MAX_TIME_STEP, delta))
    }

    override fun dispose() {
        graphicsRenderer.dispose()
    }
}
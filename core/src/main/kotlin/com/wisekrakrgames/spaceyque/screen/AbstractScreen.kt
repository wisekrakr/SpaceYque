package com.wisekrakrgames.spaceyque.screen

import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.GameEngine
import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer
import ktx.app.KtxScreen

const val WORLD_WIDTH = 9f
const val WORLD_HEIGHT = 16f
const val UNIT_SCALE = 1/16f

abstract class AbstractScreen(
        val game: SpaceYque,
        val gameEngine: GameEngine = game.gameEngine,
        val graphicsRenderer: GraphicsRenderer = game.graphicsRenderer,
        val viewport: Viewport = game.viewport
) : KtxScreen{

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }
}
package com.wisekrakrgames.spaceyque

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.viewport.FitViewport
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer
import com.wisekrakrgames.spaceyque.screen.*
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<SpaceYque>()

class SpaceYque : KtxGame<AbstractScreen>() {
    val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
    val graphicsRenderer: GraphicsRenderer by lazy {  GraphicsRenderer() }
    val gameEngine: GameEngine by lazy { GameEngine(viewport, graphicsRenderer).apply {

        addAllSystems()
    } }
    val spaceEngine: SpaceEngine by lazy { SpaceEngine(this) }

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug { "Initiated Game" }

        graphicsRenderer.init()
        //creates multiple entities and places a texture on top of it
        spaceEngine.initEntities(gameEngine, graphicsRenderer)

        addScreen(GameScreen(this))
        addScreen(SecondScreen(this))
        setScreen<GameScreen>()
    }

}
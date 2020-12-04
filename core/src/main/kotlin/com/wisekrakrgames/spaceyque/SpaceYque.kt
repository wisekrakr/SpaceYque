package com.wisekrakrgames.spaceyque

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.viewport.FitViewport
import com.wisekrakrgames.spaceyque.event.GameEventManager
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer
import com.wisekrakrgames.spaceyque.screen.*
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<SpaceYque>()

class SpaceYque : KtxGame<AbstractScreen>() {
    val uiViewport = FitViewport(UI_WIDTH, UI_HEIGHT)
    val worldViewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
    val graphicsRenderer: GraphicsRenderer by lazy {  GraphicsRenderer() }
    val gameEventManager by lazy { GameEventManager() }
    val spaceEngine: SpaceEngine by lazy { SpaceEngine(this, gameEventManager) }
    val gameEngine: GameEngine by lazy { GameEngine(gameEventManager, worldViewport, uiViewport, graphicsRenderer).apply {
        addAllSystems()
    } }


    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug { "Initiated Game" }

        graphicsRenderer.init()
        //creates multiple entities and places a texture on top of it
        spaceEngine.initEntities()

        addScreen(GameScreen(this))
        addScreen(SecondScreen(this))
        setScreen<GameScreen>()
    }

}
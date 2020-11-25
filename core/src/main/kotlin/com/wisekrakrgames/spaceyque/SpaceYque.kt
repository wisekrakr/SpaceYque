package com.wisekrakrgames.spaceyque

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer
import com.wisekrakrgames.spaceyque.screen.AbstractScreen
import com.wisekrakrgames.spaceyque.screen.GameScreen
import com.wisekrakrgames.spaceyque.screen.SecondScreen
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<SpaceYque>()

class SpaceYque : KtxGame<AbstractScreen>() {
    val graphicsRenderer: GraphicsRenderer by lazy {  GraphicsRenderer() }
    val gameEngine: GameEngine by lazy { GameEngine() }
    val spaceEngine: SpaceEngine by lazy { SpaceEngine(this) }

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug { "Initiated Game" }

        graphicsRenderer.init()
        spaceEngine.initEntities()

        addScreen(GameScreen(this))
        addScreen(SecondScreen(this))
        setScreen<GameScreen>()
    }

}
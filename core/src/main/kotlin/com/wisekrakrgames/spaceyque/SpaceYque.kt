package com.wisekrakrgames.spaceyque

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.wisekrakrgames.spaceyque.audiovisual.audio.AudioService
import com.wisekrakrgames.spaceyque.audiovisual.audio.DefaultAudioService
import com.wisekrakrgames.spaceyque.audiovisual.screen.*
import com.wisekrakrgames.spaceyque.engine.GameEngine
import com.wisekrakrgames.spaceyque.event.GameEventManager
import com.wisekrakrgames.spaceyque.engine.SpaceEngine
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<SpaceYque>()

class SpaceYque : KtxGame<KtxScreen>() {
    val uiViewport = FitViewport(UI_WIDTH, UI_HEIGHT)
    val worldViewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
    val assets: AssetStorage by lazy {
        KtxAsync.initiate()
        AssetStorage()
    }
    val audioService: AudioService by lazy { DefaultAudioService(assets) }
    val preferences: Preferences by lazy { Gdx.app.getPreferences("space-y-que") }
    val batch: Batch by lazy { SpriteBatch() }
    val gameEventManager by lazy { GameEventManager() }
    val gameEngine: GameEngine by lazy { GameEngine(this).apply { addAllSystems() } }
    val spaceEngine: SpaceEngine by lazy { SpaceEngine(this, gameEngine, gameEventManager) }


    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug { "Initiated Game " }

        addScreen(LoadingScreen(this))
        setScreen<LoadingScreen>()
    }

    override fun dispose() {
        super.dispose()
        assets.dispose()
        batch.dispose()
    }
}
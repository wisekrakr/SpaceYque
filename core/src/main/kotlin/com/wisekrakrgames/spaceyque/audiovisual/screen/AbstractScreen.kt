package com.wisekrakrgames.spaceyque.audiovisual.screen

import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.audiovisual.audio.AudioService
import com.wisekrakrgames.spaceyque.engine.GameEngine
import com.wisekrakrgames.spaceyque.engine.SpaceEngine
import com.wisekrakrgames.spaceyque.event.GameEventManager
import ktx.app.KtxScreen

const val WORLD_WIDTH = 9f
const val WORLD_HEIGHT = 16f
const val UI_WIDTH = 135f
const val UI_HEIGHT = 240f
const val UNIT_SCALE = 1/16f

abstract class AbstractScreen(
    val game: SpaceYque,
    val worldViewport: Viewport = game.worldViewport,
    val uiViewport: Viewport = game.uiViewport,
    val spaceEngine: SpaceEngine = game.spaceEngine,
    val gameEngine: GameEngine = game.gameEngine,
    val gameEventManager: GameEventManager = game.gameEventManager,
    val audioService: AudioService = game.audioService
) : KtxScreen{

    override fun resize(width: Int, height: Int) {
        worldViewport.update(width, height, true)
        uiViewport.update(width, height, true)
    }


}
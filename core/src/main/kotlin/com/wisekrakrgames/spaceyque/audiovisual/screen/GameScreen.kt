package com.wisekrakrgames.spaceyque.audiovisual.screen

import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.audiovisual.MusicAsset
import com.wisekrakrgames.spaceyque.entity.system.core.MAX_TIME_STEP
import ktx.log.debug
import ktx.log.logger
import kotlin.math.min

private val LOG = logger<GameScreen>()

class GameScreen(game: SpaceYque) : AbstractScreen(game){

    override fun show() {
        LOG.debug { "Game Screen is ON!" }

        // creates multiple entities and places a texture on top of it
        spaceEngine.initEntities()

        // start playing the game music
        audioService.play(MusicAsset.GAME, 0.3f)
    }

    override fun render(delta: Float) {
        gameEngine.update(min(MAX_TIME_STEP, delta))

        // update requests in audio service requests pool
        audioService.update()
    }

    override fun dispose() {

    }
}
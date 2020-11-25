package com.wisekrakrgames.spaceyque.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.wisekrakrgames.spaceyque.SpaceYque
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<SecondScreen>()

class SecondScreen(game: SpaceYque) : AbstractScreen(game) {
    override fun show() {
        LOG.debug { "Second Screen is ON!" }

    }

    override fun render(delta: Float) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1))
            game.setScreen<GameScreen>()
    }
}
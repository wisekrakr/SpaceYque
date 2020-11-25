package com.wisekrakrgames.spaceyque

import com.badlogic.gdx.Game

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class SpaceYqueMain : Game() {
    override fun create() {
        setScreen(FirstScreen())
    }
}
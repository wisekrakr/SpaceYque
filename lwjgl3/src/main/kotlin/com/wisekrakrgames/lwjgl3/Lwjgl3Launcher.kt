package com.wisekrakr.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.audiovisual.screen.WORLD_HEIGHT
import com.wisekrakrgames.spaceyque.audiovisual.screen.WORLD_WIDTH


fun main() {
    Lwjgl3Application(SpaceYque(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("SpaceYque")
        setWindowedMode((WORLD_WIDTH * 32).toInt(), (WORLD_HEIGHT * 32).toInt())
        setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
    })
}




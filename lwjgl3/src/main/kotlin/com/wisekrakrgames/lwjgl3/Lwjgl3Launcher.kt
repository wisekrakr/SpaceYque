package com.wisekrakr.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.wisekrakrgames.spaceyque.SpaceYqueMain


fun main() {
    Lwjgl3Application(SpaceYqueMain(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("SpaceYque")
        setWindowedMode(640, 480)
        setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
    })
}




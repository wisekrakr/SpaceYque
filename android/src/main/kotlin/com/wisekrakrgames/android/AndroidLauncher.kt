package com.wisekrakrgames.android

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.wisekrakrgames.spaceyque.SpaceYqueMain

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(SpaceYqueMain(), AndroidApplicationConfiguration())
    }
}
package com.wisekrakrgames.spaceyque.audiovisual

import com.badlogic.gdx.utils.Pool


class SoundRequest: Pool.Poolable{
    lateinit var soundAsset: SoundAsset
    var volume = 1f

    override fun reset() {
        volume = 1f
    }
}

class SoundRequestPool: Pool<SoundRequest>(){
    override fun newObject() = SoundRequest()
}


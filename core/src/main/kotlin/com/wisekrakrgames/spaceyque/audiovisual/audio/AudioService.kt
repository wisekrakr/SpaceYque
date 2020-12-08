package com.wisekrakrgames.spaceyque.audiovisual.audio

import com.wisekrakrgames.spaceyque.audiovisual.MusicAsset
import com.wisekrakrgames.spaceyque.audiovisual.SoundAsset

interface AudioService{
    var enabled: Boolean
    fun play(soundAsset: SoundAsset, volume:Float = 1f)
    fun play(musicAsset: MusicAsset, volume:Float = 1f, loop: Boolean = true)
    fun pause()
    fun resume()
    fun stop(clearAudio:Boolean = true)
    fun update()
}
package com.wisekrakrgames.spaceyque.audiovisual.audio

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.wisekrakrgames.spaceyque.audiovisual.MusicAsset
import com.wisekrakrgames.spaceyque.audiovisual.SoundAsset
import com.wisekrakrgames.spaceyque.audiovisual.SoundRequest
import com.wisekrakrgames.spaceyque.audiovisual.SoundRequestPool
import kotlinx.coroutines.launch
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.log.debug
import ktx.log.error
import ktx.log.logger
import java.util.*
import kotlin.math.max

private val LOG = logger<DefaultAudioService>()
private const val MAX_SOUND_INSTANCES = 10

class DefaultAudioService(private val assets: AssetStorage): AudioService {
    override var enabled = true
        set(value) {
            when (value) {
                true -> currentMusic?.play()
                false -> currentMusic?.pause()
            }
            field = value
        }

    private val soundCache = EnumMap<SoundAsset, Sound>(SoundAsset::class.java)
    private val soundRequestPool = SoundRequestPool()
    private val soundRequests = EnumMap<SoundAsset, SoundRequest>(SoundAsset::class.java)
    private var currentMusic: Music? = null

    override fun play(soundAsset: SoundAsset, volume: Float) {
        if (!enabled) return
        when{
            // sound request is already in queue
            soundAsset in soundRequests ->{
                LOG.debug { "Duplicated sound request for sound $soundAsset" }
                // same sound request is done in one frame multiple times
                // play sound with the highest volume of both requests once
                soundRequests[soundAsset]?.let { request ->
                    request.volume = max(request.volume, volume)
                }
            }
            // if sound requests exceeds the maximum, return
            soundRequests.size >= MAX_SOUND_INSTANCES ->{
                LOG.debug { "Maximum sound requests reached -> starting again" }
                return
            }
            else ->{
                if(soundAsset.descriptor !in assets){
                    LOG.error { "Trying to play a sound that has not yet been loaded: $soundAsset" }
                    return
                }else if(soundAsset !in soundCache){
                    soundCache[soundAsset] = assets[soundAsset.descriptor]
                }
                // add a new sound request in the request queue for use
                soundRequests[soundAsset] = soundRequestPool.obtain().apply {
                    this.soundAsset = soundAsset
                    this.volume = volume
                }
            }
        }
    }

    /**
     * The game should only have one or two music objects stored in memory
     * Here we unload the music object that is not used and play the one that we want to use currently
     */
    override fun play(musicAsset: MusicAsset, volume: Float, loop: Boolean) {
        // if there is already music playing, stop it and unload it
        if (currentMusic != null) {
            currentMusic?.stop()
        }

        if (musicAsset.descriptor !in assets) {
            LOG.error { "Music $musicAsset is not loaded" }
            return
        }

        currentMusic = assets[musicAsset.descriptor]
        if (!enabled) return

        currentMusic?.apply {
            this.volume = volume
            this.isLooping = loop
            play()
        }
    }

    override fun pause() {
        currentMusic?.pause()
    }

    override fun resume() {
        if (!enabled) return

        currentMusic?.play()
    }

    override fun stop(clearAudio: Boolean) {
        currentMusic?.stop()
        if(clearAudio){
            soundRequests.clear()
        }
    }

    override fun update() {
        if(!soundRequests.isEmpty()){
            soundRequests.values.forEach { request ->
                // get request out of cache and play it
                soundCache[request.soundAsset]?.play(request.volume)
                // put it back in the pool for reuse
                soundRequestPool.free(request)
            }
            soundRequests.clear()
        }
    }
}
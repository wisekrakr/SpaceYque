package com.wisekrakrgames.spaceyque.audiovisual

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Disposable

class GameAssetManager : Disposable {
    var assetManager = AssetManager()

    /*
     * All the Sounds loaded in the the AssetManager
     */
    fun loadSounds() {
        assetManager.load("sound/block.mp3", Sound::class.java)
        assetManager.load("sound/boost1.mp3", Sound::class.java)
        assetManager.load("sound/boost2.mp3", Sound::class.java)
        assetManager.load("sound/damage.wav", Sound::class.java)
        assetManager.load("sound/dead.mp3", Sound::class.java)
        assetManager.load("sound/explosion.mp3", Sound::class.java)
        assetManager.load("sound/health.mp3", Sound::class.java)
        assetManager.load("sound/ready.mp3", Sound::class.java)
        assetManager.load("sound/shield.mp3", Sound::class.java)
        assetManager.finishLoading()
    }

    /*
     * All the Fonts loaded in the the AssetManager
     */
    fun loadFonts() {
        assetManager.load("font/gamerFont.fnt", BitmapFont::class.java)
        assetManager.finishLoading()
    }

    /*
     * All the Skins loaded in the the AssetManager
     */
    fun loadSkins() {
        val skinParameterUISkin = SkinParameter("font/uiskin.atlas")
        assetManager.load("font/uiskin.json", Skin::class.java, skinParameterUISkin)
        val skinParameterFlatEarthSkin = SkinParameter("font/flat-earth-ui.atlas")
        assetManager.load("font/flat-earth-ui.json", Skin::class.java, skinParameterFlatEarthSkin)
        assetManager.finishLoading()
        assetManager.finishLoading()
    }

    /*
     * All the Textures loaded in the the AssetManager
     */
    fun loadTextures() {
        assetManager.load("graphics/bg1.png", Texture::class.java)
        assetManager.finishLoading()
    }

    /*
     * All the Music loaded in the the AssetManager
     */
    fun loadMusic() {
        assetManager.finishLoading()
    }

    fun queueGameImages() {
        assetManager.load("graphics/graphics.atlas", TextureAtlas::class.java)
    }

    override fun dispose() {
        assetManager.dispose()
    }
}
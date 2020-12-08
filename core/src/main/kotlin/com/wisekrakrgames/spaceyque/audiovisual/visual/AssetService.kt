package com.wisekrakrgames.spaceyque.audiovisual.visual

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.wisekrakrgames.spaceyque.audiovisual.TextureAtlasAsset
import ktx.assets.async.AssetStorage

interface AssetService{
    fun getTextureAtlasAsset(fileName: String) : TextureAtlas
    fun getTextureAsset(fileName: String): Texture
    fun getSoundAsset(fileName: String): Sound
    fun getMusicAsset(fileName: String): Music
}



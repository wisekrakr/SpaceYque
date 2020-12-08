package com.wisekrakrgames.spaceyque.audiovisual

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.loaders.BitmapFontLoader
import com.badlogic.gdx.assets.loaders.ShaderProgramLoader
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.utils.I18NBundle
import ktx.collections.gdxArrayOf

enum class TextureAtlasAsset(
        val isSkinAtlas: Boolean,
        fileName: String,
        directory: String = "graphics",
        val descriptor: AssetDescriptor<TextureAtlas> = AssetDescriptor("$directory/$fileName", TextureAtlas::class.java)
) {
    GRAPHICS( false,"graphics.atlas"),
    UI(true, "ui.atlas", "ui")
}

enum class TextureAsset(
        fileName: String,
        directory: String = "graphics",
        val descriptor: AssetDescriptor<Texture> = AssetDescriptor("$directory/$fileName", Texture::class.java)
) {
    BACKGROUND_DEFAULT("bg1.gif"),

}

enum class SoundAsset(
        fileName: String,
        directory: String = "sound",
        val descriptor: AssetDescriptor<Sound> = AssetDescriptor("$directory/$fileName", Sound::class.java)
) {
    SHORT_BOOST("boost1.mp3"),
    LONG_BOOST("boost2.mp3"),
    EXPLOSION("explosion.mp3"),
    HEALTH("health.mp3"),
    SHIELD("shield.mp3"),
    DAMAGE("damage.wav"),
    BLOCK("block.mp3"),
    SPAWN("ready.mp3"),
    DEAD("dead.mp3"),

}

enum class MusicAsset(
        fileName: String,
        directory: String = "music",
        val descriptor: AssetDescriptor<Music> = AssetDescriptor("$directory/$fileName", Music::class.java)
) {
    GAME("game.mp3"),
    //GAME_OVER("dead.mp3"),
    MENU("menu.mp3"),
    WIN("win.mp3")
}

enum class ShaderProgramAsset(
        vertexFileName: String,
        fragmentFileName: String,
        directory: String = "shader",
        val descriptor: AssetDescriptor<ShaderProgram> = AssetDescriptor(
                "$directory/$vertexFileName/$fragmentFileName",
                ShaderProgram::class.java,
                ShaderProgramLoader.ShaderProgramParameter().apply {
                    vertexFile = "$directory/$vertexFileName"
                    fragmentFile = "$directory/$fragmentFileName"
                })
) {
    OUTLINE("default.vert", "outline.frag")
}

enum class BitmapFontAsset(
        fileName: String,
        directory: String = "ui",
        val descriptor: AssetDescriptor<BitmapFont> = AssetDescriptor(
                "$directory/$fileName",
                BitmapFont::class.java,
                BitmapFontLoader.BitmapFontParameter().apply {
                    atlasName = TextureAtlasAsset.UI.descriptor.fileName
                })
) {
    FONT_LARGE_GRADIENT("font11_gradient.fnt"),
    FONT_DEFAULT("font8.fnt")
}

enum class I18NBundleAsset(
        fileName: String,
        directory: String = "i18n",
        val descriptor: AssetDescriptor<I18NBundle> = AssetDescriptor("$directory/$fileName", I18NBundle::class.java)
) {
    DEFAULT("i18n")
}



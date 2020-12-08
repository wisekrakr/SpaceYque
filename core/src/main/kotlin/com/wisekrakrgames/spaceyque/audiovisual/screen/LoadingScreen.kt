package com.wisekrakrgames.spaceyque.audiovisual.screen

import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.audiovisual.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<LoadingScreen>()
private const val ACTOR_FADE_IN_TIME = 0.5f
private const val ACTOR_FADE_OUT_TIME = 1f
private const val OFFSET_TITLE_Y = 15f
private const val ELEMENT_PADDING = 7f
private const val MENU_ELEMENT_OFFSET_TITLE_Y = 20f

/**
 * LoadingScreen is not an AbstractScreen because we need to load all assets first.
 * An AbstractScreen (like GameScreen) needs the assets.
 * This way we wont have any MissingAssetExceptions
 */
class LoadingScreen(
        private val game: SpaceYque,
        private val assets: AssetStorage = game.assets
) : KtxScreen {

    override fun show() {
        LOG.debug{ "Starting to load assets "}

        val old = System.currentTimeMillis()
        // queue asset loading
        val assetReferences = gdxArrayOf(
                TextureAsset.values().map{assets.loadAsync(it.descriptor)},
                TextureAtlasAsset.values().map { assets.loadAsync(it.descriptor) },
                SoundAsset.values().map { assets.loadAsync(it.descriptor) },
                MusicAsset.values().map { assets.loadAsync(it.descriptor) },
                BitmapFontAsset.values().map { assets.loadAsync(it.descriptor) },
                ShaderProgramAsset.values().map { assets.loadAsync(it.descriptor) },
        ).flatten()

        // when assets are loaded, change to Game Screen
        KtxAsync.launch {
            // wait for all assets to load
            assetReferences.joinAll()
            // loading is done
            LOG.debug { "It took ${(System.currentTimeMillis() - old) * 0.001f} seconds to load assets and initialize" }
            assetsLoaded()
        }
    }

    private fun assetsLoaded() {
        game.addScreen(GameScreen(game))
        game.setScreen<GameScreen>()
        game.removeScreen<LoadingScreen>()
        dispose()
    }

}
package com.wisekrakrgames.spaceyque.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.SpaceEngine
import com.wisekrakrgames.spaceyque.entity.component.GraphicComponent
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<GraphicsRenderer>()

class GraphicsRenderer : SpriteBatch(){

    //A mutablelist for textures that need to be disposed
    private var graphicsTextures = mutableListOf<Texture>()
    //A mutablelist for sprites that need to be rendered
    private var graphicsSprites = mutableListOf<Sprite>()

    val playerTexture = Texture(Gdx.files.internal("graphics/player_ship.png"))

    fun init(){
        setAllGraphics()
    }

    private fun setAllGraphics(){
        graphicsTextures.add(playerTexture)
    }

    fun renderAllGraphics(viewport: Viewport, spaceEngine: SpaceEngine){
        for (entity in spaceEngine.gameEntities){
            try {
                use(viewport.camera.combined) {batch ->
                    when(entity.tag){

                        "PLAYER" -> spaceEngine.player.instance[GraphicComponent.mapper]?.let { graphicComponent ->
                            spaceEngine.player.instance[TransformComponent.mapper]?.let { transformComponent ->

                                graphicComponent.sprite.run {
                                    rotation = transformComponent.rotation
                                    setBounds(
                                            transformComponent.position.x, transformComponent.position.y,
                                            transformComponent.size.x, transformComponent.size.y
                                    )
                                    draw(batch)
                                }
                            }
                        }
                    }
                }
            }catch (t: Throwable){
                throw IllegalArgumentException("Could not draw $entity", t)
            }
        }
//        for (sprite in graphicsSprites){
//            try {
//                use(viewport.camera.combined) {
//                    sprite.draw(it)
//                }
//            }catch (t: Throwable){
//                throw IllegalArgumentException("Could not draw $sprite", t)
//            }
//        }
    }

    override fun dispose() {
        super.dispose()
        for (t in graphicsTextures){
            try {
                t.dispose()
            }catch (e: Throwable){
                throw IllegalArgumentException("Could not dispose $t", e)
            }
        }

        LOG.debug { "Sprites in batch: ${(this as SpriteBatch).maxSpritesInBatch}" }
    }
}



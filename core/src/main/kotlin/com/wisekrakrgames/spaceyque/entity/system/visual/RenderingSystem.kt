package com.wisekrakrgames.spaceyque.entity.system.visual

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.engine.GameEngine
import com.wisekrakrgames.spaceyque.entity.component.GraphicComponent
import com.wisekrakrgames.spaceyque.entity.component.PowerUpType
import com.wisekrakrgames.spaceyque.entity.component.TransformComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getGraphicsComponent
import com.wisekrakrgames.spaceyque.entity.system.core.ComponentMapperHolder.Companion.getTransformComponent
import com.wisekrakrgames.spaceyque.event.GameEvent
import com.wisekrakrgames.spaceyque.event.GameEventListener
import com.wisekrakrgames.spaceyque.event.GameEventManager
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.error
import ktx.log.logger
import kotlin.math.min

private val LOG = logger<RenderingSystem>()
private const val BGD_SCROLL_SPEED_X = 0.03f
private const val MIN_BGD_SCROLL_SPEED_Y = -0.25f
private const val OUTLINE_COLOR_RED = 0f
private const val OUTLINE_COLOR_GREEN = 113f / 255f
private const val OUTLINE_COLOR_BLUE = 214f / 255f
private const val OUTLINE_COLOR_MIN_ALPHA = 0.35f
private const val BGD_SCROLL_SPEED_SHORT_BOOST = 0.25f
private const val BGD_SCROLL_SPEED_LONG_BOOST = 0.5f
private const val TIME_TO_RESET_BGD_SCROLL_SPEED = 10f // in seconds

class RenderingSystem(
        private val gameEngine: GameEngine,
        private val gameEventManager: GameEventManager,
        private val batch: Batch,
        private val worldViewport: Viewport,
        private val uiViewport: Viewport,
        background: Texture,
        private val outlineShaderProgram: ShaderProgram
) : SortedIteratingSystem(
        allOf(TransformComponent::class, GraphicComponent::class).get(), compareBy { entity -> entity[TransformComponent.mapper] }
), GameEventListener{

    private val backgroundSprite = Sprite(background.apply {
        setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    })
    private val backgroundScrollSpeed = Vector2(0.05f,-0.25f)

    private val textureSizeLocation = outlineShaderProgram.getUniformLocation("u_textureSize")
    private val outlineColorLocation = outlineShaderProgram.getUniformLocation("u_outlineColor")
    private val outlineColor = Color(0f, 113f/255f, 214f/255f, 1f)

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        gameEventManager.addListener(GameEvent.PowerUp::class, this)
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        gameEventManager.removeListener(GameEvent.PowerUp::class, this)
    }

    override fun update(deltaTime: Float) {
        uiViewport.apply()

        //background rendering
        batch.use(uiViewport.camera.combined) {
            backgroundSprite.run {
                backgroundScrollSpeed.y = min(-0.25f, backgroundScrollSpeed.y + deltaTime * (1f/5f))
                scroll(backgroundScrollSpeed.x * deltaTime, backgroundScrollSpeed.y * deltaTime)
                draw(batch)
            }
        }

        //forces an entity in front of the other if the y coord is lower
        forceSort()
        worldViewport.apply()

        //entity rendering
        batch.use(worldViewport.camera.combined) {
            super.update(deltaTime)
        }

        // render outlines of entities
        renderEntityOutlines()
    }

    private fun renderEntityOutlines() {
        // flush because we change the shader here
        batch.use(worldViewport.camera.combined){
            it.shader = outlineShaderProgram

            gameEngine.getPlayerEntities.forEach{ entity ->
                renderPlayerOutlines(entity, it)
            }

            // turn back to the default shader. Set to null to use internal default shader
            it.shader = null
        }
    }

    private fun renderPlayerOutlines(entity: Entity, it: Batch) {
        val player = ComponentMapperHolder.getPlayerComponent(entity)

        if(player.shield > 0f){
            outlineColor.a = MathUtils.clamp(player.shield / player.maxShield, 0f, 1f)
            // pass color value from program to shader
            outlineShaderProgram.setUniformf(outlineColorLocation, outlineColor)
            // pass texture size from program to shader
            entity[GraphicComponent.mapper]?.let { graphic ->
                graphic.sprite.run {
                    outlineShaderProgram.setUniformf(textureSizeLocation, texture.width.toFloat(), texture.height.toFloat())
                    draw(batch)
                }
            }
        }

    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        //We already have these components, but Ashley sometimes messes things up, so this is for extra security
        val transform = getTransformComponent(entity)
        val graphic = getGraphicsComponent(entity)

        if(graphic.sprite.texture == null){
            LOG.error { "Entity has no texture to render. Entity: $entity" }
            return
        }

        //place the sprite on top of the calculated position of the entity
        graphic.sprite.run {
            setOrigin(transform.interpolatedPosition.x + (transform.size.x * 0.5f),
                    transform.interpolatedPosition.y + (transform.size.y * 0.5f))

            setPosition(transform.interpolatedPosition.x, transform.interpolatedPosition.y)
            setSize(transform.size.x, transform.size.y)

            rotation = transform.rotation
            draw(batch)
        }
    }

    override fun onEvent(event: GameEvent) {
        val eventPowerUp = event as GameEvent.PowerUp

        if (eventPowerUp.type == PowerUpType.SHORT_BOOST) {
            backgroundScrollSpeed.y -= BGD_SCROLL_SPEED_SHORT_BOOST
        } else if (eventPowerUp.type == PowerUpType.LONG_BOOST) {
            backgroundScrollSpeed.y -= BGD_SCROLL_SPEED_LONG_BOOST
        }
    }
}
package com.wisekrakrgames.spaceyque.entity.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.GdxRuntimeException
import com.wisekrakrgames.spaceyque.entity.component.Animation2D
import com.wisekrakrgames.spaceyque.entity.component.AnimationComponent
import com.wisekrakrgames.spaceyque.entity.component.AnimationType
import com.wisekrakrgames.spaceyque.entity.component.GraphicComponent
import com.wisekrakrgames.spaceyque.entity.system.ComponentMapperHolder.Companion.getAnimationComponent
import com.wisekrakrgames.spaceyque.entity.system.ComponentMapperHolder.Companion.getGraphicsComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.debug
import ktx.log.error
import ktx.log.logger
import java.util.*

private val LOG = logger<AnimationSystem>()

class AnimationSystem(
        private val atlas: TextureAtlas
) : IteratingSystem(allOf(AnimationComponent::class, GraphicComponent::class).get()), EntityListener{
    private val animationCache = EnumMap<AnimationType, Animation2D>(AnimationType::class.java)

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(family, this)
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        engine.removeEntityListener(this)
    }

    override fun entityAdded(entity: Entity) {
        entity[AnimationComponent.mapper]?.let { animationComponent ->
            animationComponent.animation = getAnimation(animationComponent.type)
            val frame = animationComponent.animation.getKeyFrame(animationComponent.stateTime)
            getGraphicsComponent(entity).setSpriteRegion(frame)
        }
    }
    override fun entityRemoved(entity: Entity) = Unit

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val animationComponent = getAnimationComponent(entity)
        val graphic = getGraphicsComponent(entity)

        when(animationComponent.type){
            AnimationType.NONE -> LOG.error { "No type specified for the animation component $animationComponent for Entity: $entity" }
            animationComponent.animation.type -> animationComponent.stateTime += deltaTime
            else -> {
                animationComponent.stateTime = 0f
                animationComponent.animation = getAnimation(animationComponent.type)
            }
        }

        val frame = animationComponent.animation.getKeyFrame(animationComponent.stateTime)
        graphic.setSpriteRegion(frame)

    }

    /**
     * Load the correct frame for this animation
     */
    private fun getAnimation(type: AnimationType): Animation2D {
        //is there an animation of this type in the cache. If yes, it gets loaded
        var animation = animationCache[type]
        if(animation == null){
            //if there is no animation of this type in the cache: Gets the regions out of the atlas
            var regions = atlas.findRegions(type.atlasKey)
            if(regions.isEmpty){
                //if there are no regions. We get the error region out of the atlas
                LOG.error { "No regions found for ${type.atlasKey}" }
                regions = atlas.findRegions("error")
                //if the error region is not there, throws a GDXRuntimeException and closes the game
                if(regions.isEmpty) throw GdxRuntimeException("There is no error region in the atlas")
            }else{
                LOG.debug { "Adding animation of type $type with ${regions.size} regions" }
            }
            //if we found regions, we create a new Animation2D
            animation = Animation2D(type, regions, type.playMode, type.speedRate)
            //store the new animation in the cache
            animationCache[type] = animation
        }
        return animation
    }


}
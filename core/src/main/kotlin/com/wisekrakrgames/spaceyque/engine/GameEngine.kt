package com.wisekrakrgames.spaceyque.engine

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.wisekrakrgames.spaceyque.SpaceYque
import com.wisekrakrgames.spaceyque.audiovisual.ShaderProgramAsset
import com.wisekrakrgames.spaceyque.audiovisual.TextureAsset
import com.wisekrakrgames.spaceyque.audiovisual.TextureAtlasAsset
import com.wisekrakrgames.spaceyque.audiovisual.audio.AudioService
import com.wisekrakrgames.spaceyque.entity.component.PlayerComponent
import com.wisekrakrgames.spaceyque.entity.component.RemoveComponent
import com.wisekrakrgames.spaceyque.entity.system.camera.CameraSystem
import com.wisekrakrgames.spaceyque.entity.system.core.*
import com.wisekrakrgames.spaceyque.entity.system.gameobjects.AttachSystem
import com.wisekrakrgames.spaceyque.entity.system.gameobjects.BigEmptySystem
import com.wisekrakrgames.spaceyque.entity.system.gameobjects.PowerUpSystem
import com.wisekrakrgames.spaceyque.entity.system.visual.AnimationSystem
import com.wisekrakrgames.spaceyque.entity.system.visual.PlayerAnimationSystem
import com.wisekrakrgames.spaceyque.entity.system.visual.RenderingSystem
import com.wisekrakrgames.spaceyque.event.GameEventManager
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.assets.async.AssetStorage
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<GameEngine>()

class GameEngine(
     game: SpaceYque
) : PooledEngine() {

    private val gameEventManager: GameEventManager = game.gameEventManager
    private val worldViewport: Viewport = game.worldViewport
    private val uiViewport: Viewport = game.uiViewport
    private val batch: Batch = game.batch
    private val assets: AssetStorage = game.assets
    private val audioService: AudioService = game.audioService
    private val preferences: com.badlogic.gdx.Preferences = game.preferences

    private val atlas = assets[TextureAtlasAsset.GRAPHICS.descriptor]
    private val background = assets[TextureAsset.BACKGROUND_DEFAULT.descriptor]

    fun addAllSystems(){
        LOG.debug { "Adding all game systems" }

        addSystem(InputSystem(worldViewport))
        addSystem(MovementSystem(worldViewport, gameEventManager))
        addSystem(BigEmptySystem())
        addSystem(DamageSystem(gameEventManager))
        addSystem(PlayerAnimationSystem(atlas))
        addSystem(AttachSystem())
        addSystem(AnimationSystem(atlas))
        addSystem(PowerUpSystem(gameEventManager, audioService))
        addSystem(RenderingSystem(
                this,
                gameEventManager,
                batch,
                worldViewport,
                uiViewport,
                assets[TextureAsset.BACKGROUND_DEFAULT.descriptor],
                assets[ShaderProgramAsset.OUTLINE.descriptor])
        )
        addSystem(PhysicsSystem())
        addSystem(CameraSystem(worldViewport.camera, gameEventManager))

        addSystem(RemoveSystem())
        addSystem(DebugSystem(worldViewport.camera))

    }

    val getPlayerEntities: ImmutableArray<Entity> by lazy {
        getEntitiesFor(allOf(PlayerComponent::class).exclude(RemoveComponent::class).get())
    }
}
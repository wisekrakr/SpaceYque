package com.wisekrakrgames.spaceyque

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.wisekrakrgames.spaceyque.entity.AbstractEntity
import com.wisekrakrgames.spaceyque.entity.component.*
import com.wisekrakrgames.spaceyque.entity.system.gameobjects.INITIAL_DAMAGE_AREA
import com.wisekrakrgames.spaceyque.event.GameEvent
import com.wisekrakrgames.spaceyque.event.GameEventListener
import com.wisekrakrgames.spaceyque.event.GameEventManager
import com.wisekrakrgames.spaceyque.screen.UNIT_SCALE
import com.wisekrakrgames.spaceyque.screen.WORLD_HEIGHT
import com.wisekrakrgames.spaceyque.screen.WORLD_WIDTH
import ktx.ashley.entity
import ktx.ashley.with

class SpaceEngine(val game: SpaceYque, val gameEventManager: GameEventManager) : GameEventListener{

    fun initEntities(){
        gameEventManager.addListener(GameEvent.PlayerDeath::class, this)

        PlayerEntity(playerSpawnPosition())
        //BigEmptyEntity()
        for(i in 0..3)EnemyEntity(randomSpawnPosition())
    }

    private fun randomSpawnPosition(): Vector2 {
        val randomX = MathUtils.random(0f, WORLD_WIDTH)
        val randomY = MathUtils.random(0f, WORLD_HEIGHT)

        return Vector2(randomX, randomY)
    }

    private fun playerSpawnPosition(): Vector2 = Vector2(game.worldViewport.worldWidth/2, game.worldViewport.worldHeight/2 - 0.5f)

    override fun onEvent(event: GameEvent) {
        when(event){
            is GameEvent.PlayerDeath -> {
                PlayerEntity(playerSpawnPosition())
            }
            GameEvent.PlayerSpawn -> TODO()
            GameEvent.PlayerBlock -> TODO()
            GameEvent.PlayerMove -> TODO()
            GameEvent.PlayerHit -> TODO()
            GameEvent.PowerUp -> TODO()
        }
    }

    inner class PlayerEntity(pos : Vector2): AbstractEntity(tag = "PLAYER") {

        init {
            val instance = game.gameEngine.entity {
                with<PlayerComponent>()
                with<TransformComponent>{
                    setInitialPosition(pos.x,pos.y,-1f)
                }
                with<GraphicComponent>()
                with<PlayerDirectionTextureComponent>()
                with<MovementComponent>()
            }

            game.gameEngine.entity {
                with<TransformComponent>(){
                    size.set(0.3f, 0.6f)
                    position.z = -1f
                }
                with<AttachComponent>(){
                    entity = instance
                    offset.set(5.8f * UNIT_SCALE, -8f * UNIT_SCALE )
                }
                with<GraphicComponent>()
                with<AnimationComponent> {type = AnimationType.EXHAUST}

            }
        }
    }

    inner class EnemyEntity(pos: Vector2) : AbstractEntity(tag = "Enemy"){

        init {
            game.gameEngine.entity {
                with<TransformComponent> { position.set(pos.x, pos.y, 0f) }
//            with<GraphicComponent>{setSpriteRegion(region)}
            }
        }
    }

    inner class BigEmptyEntity(): AbstractEntity(tag = "BigEmpty") {
        init {
            game.gameEngine.entity {
                with<TransformComponent> {
                    size.set(WORLD_WIDTH, INITIAL_DAMAGE_AREA)
                    position.z = -100f
                }
                with<GraphicComponent>()
                with<AnimationComponent>{type = AnimationType.BIG_EMPTY}
                with<BigEmptyComponent>()
            }
        }
    }



}
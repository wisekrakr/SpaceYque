package com.wisekrakrgames.spaceyque

import com.wisekrakrgames.spaceyque.entity.PlayerEntity
import com.wisekrakrgames.spaceyque.factory.EntityFactory
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer

class SpaceEngine(game: SpaceYque) {
    private val entityFactory = EntityFactory(game.gameEngine)

    val player = PlayerEntity(game, game.viewport.worldWidth/2, game.viewport.worldHeight/2)

    fun initEntities(graphicsRenderer: GraphicsRenderer){

        for(i in 1..3){
            entityFactory.spawnEntity( graphicsRenderer.graphics.findRegion("enemyBlack4"))
            entityFactory.spawnEntity(graphicsRenderer.graphics.findRegion("enemyRed1"))
        }

        entityFactory.spawnTheBigEmpty()
    }
}
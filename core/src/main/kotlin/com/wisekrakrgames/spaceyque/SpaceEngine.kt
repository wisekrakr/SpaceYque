package com.wisekrakrgames.spaceyque

import com.wisekrakrgames.spaceyque.entity.PlayerEntity
import com.wisekrakrgames.spaceyque.factory.EntityFactory
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer

class SpaceEngine(game: SpaceYque) {
    private val entityFactory = EntityFactory()


    val player = PlayerEntity(game, game.viewport.worldWidth/2, game.viewport.worldHeight/2)

    fun initEntities(gameEngine: GameEngine, graphicsRenderer: GraphicsRenderer){

        for(i in 1..3){
            entityFactory.spawnEntity(gameEngine, graphicsRenderer.graphicsAtlas.findRegion("enemyBlack4"))
            entityFactory.spawnEntity(gameEngine, graphicsRenderer.graphicsAtlas.findRegion("enemyRed1"))
        }


    }


}
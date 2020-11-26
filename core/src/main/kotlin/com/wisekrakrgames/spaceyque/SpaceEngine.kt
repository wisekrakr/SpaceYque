package com.wisekrakrgames.spaceyque

import com.wisekrakrgames.spaceyque.entity.PlayerEntity
import com.wisekrakrgames.spaceyque.factory.EntityFactory
import com.wisekrakrgames.spaceyque.graphics.GraphicsRenderer

class SpaceEngine(game: SpaceYque) {
    private val entityFactory = EntityFactory()


    private val player = PlayerEntity(game)

    fun initEntities(gameEngine: GameEngine, graphicsRenderer: GraphicsRenderer){

//        entityFactory.spawnEntity(gameEngine, graphicsRenderer.graphicsAtlas.findRegion("enemy01_ship"))
//        entityFactory.spawnEntity(gameEngine, graphicsRenderer.graphicsAtlas.findRegion("enemy02_ship"))

    }


}
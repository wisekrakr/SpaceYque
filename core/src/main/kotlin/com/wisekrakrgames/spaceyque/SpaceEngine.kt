package com.wisekrakrgames.spaceyque

import com.wisekrakrgames.spaceyque.entity.AbstractEntity
import com.wisekrakrgames.spaceyque.entity.PlayerEntity

class SpaceEngine(game: SpaceYque) {
    val gameEntities = mutableListOf<AbstractEntity>()

    val player = PlayerEntity(game)

    fun initEntities(){
        gameEntities.add(player)
    }
}
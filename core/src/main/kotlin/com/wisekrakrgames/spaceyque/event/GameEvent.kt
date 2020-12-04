package com.wisekrakrgames.spaceyque.event

import com.badlogic.ashley.core.Entity
import com.wisekrakrgames.spaceyque.entity.component.PowerUpType


sealed class GameEvent {
    object PlayerSpawn : GameEvent()

    object PlayerDeath : GameEvent() {
        var distance = 0f

        override fun toString() = "PlayerDeath(distance=$distance)"
    }

    object PlayerBlock : GameEvent() {
        var shield = 0f
        var maxShield = 0f

        override fun toString() = "PlayerBlock(shield=$shield,maxShield=$maxShield)"
    }

    object PlayerMove : GameEvent() {
        var distance = 0f
        var speed = 0f

        override fun toString() = "PlayerMove(distance=$distance,speed=$speed)"
    }

    object PlayerHit : GameEvent() {
        lateinit var player: Entity
        var health = 0f
        var maxHealth = 0f

        override fun toString() = "PlayerHit(player=$player,life=$health,maxLife=$maxHealth)"
    }

    object PowerUp : GameEvent() {
        lateinit var player: Entity
        var type = PowerUpType.NONE

        override fun toString() = "PowerUp(player=$player,type=$type)"
    }
}




package com.wisekrakrgames.spaceyque.entity.component

const val MAX_HEALTH = 100f
const val MAX_SHIELD = 100f

abstract class AbstractEntityComponent {
    var health = MAX_HEALTH
    var maxHealth= MAX_HEALTH
    var shield= 0f
    var maxShield= MAX_HEALTH
    var distance= 0f

}
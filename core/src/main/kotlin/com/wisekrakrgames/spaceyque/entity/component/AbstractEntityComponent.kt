package com.wisekrakrgames.spaceyque.entity.component

const val MAX_LIFE = 100f
const val MAX_SHIELD = 100f

abstract class AbstractEntityComponent {
    var life = MAX_LIFE
    var maxLife= MAX_LIFE
    var shield= 0f
    var maxShield= MAX_LIFE
    var distance= 0f
}
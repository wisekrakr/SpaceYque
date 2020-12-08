package com.wisekrakrgames.spaceyque.event

import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.GdxSet
import kotlin.reflect.KClass

private const val INITIAL_LISTENER_CAPACITY = 8

class GameEventManager {
    private val listeners = ObjectMap<KClass<out GameEvent>, GdxSet<GameEventListener>>()

    fun addListener(type: KClass<out GameEvent>, listener: GameEventListener) {
        var eventListeners = listeners[type]
        if (eventListeners == null) {
            eventListeners = GdxSet(INITIAL_LISTENER_CAPACITY)
            listeners.put(type, eventListeners)
        }

        eventListeners.add(listener)
    }

    fun removeListener(type: KClass<out GameEvent>, listener: GameEventListener) {
        listeners[type]?.remove(listener)
    }

    fun removeListener(listener: GameEventListener) {
        listeners.values().forEach { it.remove(listener) }
    }

    fun dispatchEvent(event: GameEvent) {
        listeners[event::class]?.forEach { it.onEvent(event) }
    }
}
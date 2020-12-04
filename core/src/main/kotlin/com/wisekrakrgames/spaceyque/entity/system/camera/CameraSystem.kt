package com.wisekrakrgames.spaceyque.entity.system.camera

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.Camera
import com.wisekrakrgames.spaceyque.event.GameEvent
import com.wisekrakrgames.spaceyque.event.GameEventListener
import com.wisekrakrgames.spaceyque.event.GameEventManager
import ktx.collections.GdxArray

class CameraSystem(
        camera: Camera,
        private val gameEventManager: GameEventManager
): EntitySystem(), GameEventListener {

    private val shakePool = CameraShakePool(camera)
    private val activeShakes = GdxArray<CameraShake>()

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        gameEventManager.addListener(GameEvent.PlayerHit::class, this)
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        gameEventManager.removeListener(this)
    }

    override fun update(deltaTime: Float) {
        //if there is something happening
        if(!activeShakes.isEmpty){
            val shake = activeShakes.first()
            if(shake.update(deltaTime)){
                //shake is done, remove
                activeShakes.removeIndex(0)
                //return it to the pool for reuse
                shakePool.free(shake)
            }
        }
    }

    override fun onEvent(event: GameEvent) {
        if(activeShakes.size < 3){
            activeShakes.add(shakePool.obtain().apply {
                duration = 0.15f
                maxDistortion = 0.15f
            })
        }

    }

}


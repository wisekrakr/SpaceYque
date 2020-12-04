package com.wisekrakrgames.spaceyque.entity.system.camera

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool

class CameraShake: Pool.Poolable {
    lateinit var camera: Camera

    var maxDistortion = 0f
    var duration = 0f
    private var currentDuration = 0f
    private val originalCameraPosition = Vector3()
    private var storeCameraPosition = true

    fun update(deltaTime: Float) : Boolean{
        if(storeCameraPosition){
            storeCameraPosition = false
            originalCameraPosition.set(camera.position)
        }

        //starts shaking heavily and slowly returns to normalcy
        if(currentDuration < duration){
            val currentDistortion = maxDistortion * ((duration-currentDuration)/duration)

            camera.position.x = originalCameraPosition.x + MathUtils.random(-1f, 1f) * currentDistortion
            camera.position.y = originalCameraPosition.y + MathUtils.random(-1f, 1f) * currentDistortion
            camera.update()

            currentDuration += deltaTime

            return false
        }

        camera.position.set(originalCameraPosition)
        camera.update()
        return true
    }

    override fun reset() {
        maxDistortion = 0f
        duration = 0f
        currentDuration = 0f
        originalCameraPosition.set(Vector3.Zero)
        storeCameraPosition = true
    }
}

class CameraShakePool(private val cam: Camera) : Pool<CameraShake>(){
    override fun newObject() = CameraShake().apply {
        this.camera = cam
    }
}
package com.rahul.runtimepermissions.camera

import android.content.Context
import android.hardware.Camera
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.rahul.runtimepermissions.util.calculatePreviewOrientation
import java.io.IOException

class CameraPreview @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val camera: Camera? = null,
    private val cameraInfo: Camera.CameraInfo? = null,
    private val displayOrientation: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    init {
        if (camera != null && cameraInfo != null) {
            holder.addCallback(this@CameraPreview)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            camera?.run {
                setPreviewDisplay(holder)
                startPreview()
            }
            Log.d(TAG, "Camera preview started.")
        } catch (e: IOException) {
            Log.d(TAG, "Error setting camera preview: ${e.message}")
        }

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        if (holder.surface == null) {
            Log.d(TAG, "Preview surface does not exist")
            return
        }
        Log.d(TAG, "Preview stopped.")
        camera?.run {
            stopPreview()
            cameraInfo?.let {
                setDisplayOrientation(it.calculatePreviewOrientation(displayOrientation))
            }
            setPreviewDisplay(holder)
            startPreview()
            Log.d(TAG, "Camera preview started.")
        }
    }

    companion object {
        private val TAG = "CameraPreview"
    }
}
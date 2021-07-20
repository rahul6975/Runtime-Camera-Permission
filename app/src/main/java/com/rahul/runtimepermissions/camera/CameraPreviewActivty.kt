package com.rahul.runtimepermissions.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rahul.runtimepermissions.R
import android.hardware.Camera
import android.util.Log
import android.widget.FrameLayout

private const val TAG = "CameraPreviewActivity"
private const val CAMERA_ID = 0

class CameraPreviewActivity : AppCompatActivity() {

    private var camera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        camera = getCameraInstance(CAMERA_ID)
        val cameraInfo = Camera.CameraInfo()
        Camera.getCameraInfo(CAMERA_ID, cameraInfo)

        if (camera == null) {
            setContentView(R.layout.activity_camera_unavailable)
        } else {
            setContentView(R.layout.activity_camera)
            val displayRotation = windowManager.defaultDisplay.rotation

            val cameraPreview = CameraPreview(
                this, null,
                0, camera, cameraInfo, displayRotation
            )
            findViewById<FrameLayout>(R.id.camera_preview).addView(cameraPreview)
        }
    }

    public override fun onPause() {
        super.onPause()
        releaseCamera()
    }

    private fun getCameraInstance(cameraId: Int): Camera? {
        var c: Camera? = null
        try {
            c = Camera.open(cameraId)
        } catch (e: Exception) {
            Log.e(TAG, "Camera $cameraId is not available: ${e.message}")
        }
        return c
    }

    private fun releaseCamera() {
        camera?.release()
        camera = null
    }
}
package com.rahul.runtimepermissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.rahul.runtimepermissions.camera.CameraPreviewActivity
import com.rahul.runtimepermissions.util.checkSelfPermissionCompat
import com.rahul.runtimepermissions.util.requestPermissionsCompat
import com.rahul.runtimepermissions.util.shouldShowRequestPermissionRationaleCompat
import com.rahul.runtimepermissions.util.showSnackbar

const val PERMISSION_REQUEST_CAMERA = 0

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var layout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.main_layout)

        findViewById<Button>(R.id.button_open_camera).setOnClickListener {
            showCameraPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                layout.showSnackbar(R.string.camera_permission_granted, Snackbar.LENGTH_SHORT)
                startCamera()
            } else {
                layout.showSnackbar(R.string.camera_permission_denied, Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun showCameraPreview() {
        if (checkSelfPermissionCompat(Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            layout.showSnackbar(R.string.camera_permission_available, Snackbar.LENGTH_SHORT)
            startCamera()
        } else {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.CAMERA)) {
            layout.showSnackbar(
                R.string.camera_access_required,
                Snackbar.LENGTH_INDEFINITE, R.string.ok
            ) {
                requestPermissionsCompat(
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_REQUEST_CAMERA
                )
            }

        } else {
            layout.showSnackbar(R.string.camera_permission_not_available, Snackbar.LENGTH_SHORT)
            requestPermissionsCompat(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
        }
    }

    private fun startCamera() {
        val intent = Intent(this, CameraPreviewActivity::class.java)
        startActivity(intent)
    }
}
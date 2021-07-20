package com.rahul.runtimepermissions.util

import android.hardware.Camera
import android.view.Surface

fun Camera.CameraInfo.calculatePreviewOrientation(rotation: Int): Int {
    val degrees = when (rotation) {
        Surface.ROTATION_0 -> 0
        Surface.ROTATION_90 -> 90
        Surface.ROTATION_180 -> 180
        Surface.ROTATION_270 -> 270
        else -> 0
    }

    return if (facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
        360 - ((orientation + degrees) % 360) % 360
    } else {
        (orientation - degrees + 360) % 360
    }
}
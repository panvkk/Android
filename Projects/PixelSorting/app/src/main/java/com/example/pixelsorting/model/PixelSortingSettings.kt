package com.example.pixelsorting.model

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
data class PixelSortingSettings(
    val imageUri: String,
    val sortType: SortType,
    val sortKey: SortKey,
    val effectLevel: Int
)

enum class SortType {
    ROWS,
    COLUMNS
}

enum class SortKey {
    BRIGHTNESS,
    HUE,
    RED,
    GREEN,
    BLUE
}
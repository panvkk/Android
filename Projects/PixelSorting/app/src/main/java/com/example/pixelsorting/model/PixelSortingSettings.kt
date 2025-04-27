package com.example.pixelsorting.model

import android.graphics.Bitmap
import android.net.Uri

data class PixelSortingSettings(
    val imageUri: String,
    val sortType: String,
    val sortKey: String,
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
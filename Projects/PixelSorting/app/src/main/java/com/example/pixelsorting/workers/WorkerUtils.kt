package com.example.pixelsorting.workers

import android.graphics.Bitmap
import android.graphics.Color
import com.example.pixelsorting.model.SortKey
import com.example.pixelsorting.model.SortType

private fun getKeyValue(pixel: Int, sortKey: SortKey): Double {
    return when (sortKey) {
        SortKey.BRIGHTNESS -> {
            val r = Color.red(pixel)
            val g = Color.green(pixel)
            val b = Color.blue(pixel)
            0.299 * r + 0.587 * g + 0.114 * b
        }
        SortKey.HUE -> {
            val hsv = FloatArray(3)
            Color.RGBToHSV(Color.red(pixel), Color.green(pixel), Color.blue(pixel), hsv)
            hsv[0].toDouble()
        }
        SortKey.RED -> Color.red(pixel).toDouble()
        SortKey.GREEN -> Color.green(pixel).toDouble()
        SortKey.BLUE -> Color.blue(pixel).toDouble()
    }
}

fun pixelSort(source: Bitmap, sortType: SortType, sortKey: SortKey, effectLevel: Int): Bitmap {
    val level = effectLevel.coerceIn(1, 100)
    val width = source.width
    val height = source.height
    val config = source.config ?: Bitmap.Config.ARGB_8888
    val sortedBitmap = source.copy(config, true)
    when (sortType) {
        SortType.ROWS -> {
            for (y in 0 until height) {
                val rowPixels = MutableList(width) { x -> sortedBitmap.getPixel(x, y) }
                val segmentLength = (width * level) / 100
                val segLength = if (segmentLength < 1) 1 else segmentLength
                val startIndex = (width - segLength) / 2
                val endIndex = startIndex + segLength
                val sortedSegment = rowPixels.subList(startIndex, endIndex)
                    .sortedBy { pixel -> getKeyValue(pixel, sortKey) }
                for (i in sortedSegment.indices) {
                    rowPixels[startIndex + i] = sortedSegment[i]
                }
                for (x in 0 until width) {
                    sortedBitmap.setPixel(x, y, rowPixels[x])
                }
            }
        }
        SortType.COLUMNS -> {
            for (x in 0 until width) {
                val colPixels = MutableList(height) { y -> sortedBitmap.getPixel(x, y) }
                val segmentLength = (height * level) / 100
                val segLength = if (segmentLength < 1) 1 else segmentLength
                val startIndex = (height - segLength) / 2
                val endIndex = startIndex + segLength
                val sortedSegment = colPixels.subList(startIndex, endIndex)
                    .sortedBy { pixel -> getKeyValue(pixel, sortKey) }
                for (i in sortedSegment.indices) {
                    colPixels[startIndex + i] = sortedSegment[i]
                }
                for (y in 0 until height) {
                    sortedBitmap.setPixel(x, y, colPixels[y])
                }
            }
        }
    }
    return sortedBitmap
}
package com.example.pixelsorting.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.util.Log
import com.example.pixelsorting.OUTPUT_PATH
import com.example.pixelsorting.model.SortKey
import com.example.pixelsorting.model.SortType
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

private const val TAG = "WorkerUtils"

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

@Throws(FileNotFoundException::class)
fun writeBitmapToFile(applicationContext: Context, bitmap: Bitmap): Uri {
    val name = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString())
    val outputDir = File(applicationContext.filesDir, OUTPUT_PATH)
    if (!outputDir.exists()) {
        outputDir.mkdirs() // should succeed
    }
    val outputFile = File(outputDir, name)
    var out: FileOutputStream? = null
    try {
        out = FileOutputStream(outputFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
    } finally {
        out?.let {
            try {
                it.close()
            } catch (e: IOException) {
                Log.e(TAG, e.message.toString())
            }
        }
    }
    return Uri.fromFile(outputFile)
}
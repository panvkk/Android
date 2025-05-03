package com.example.pixelsorting.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.pixelsorting.CHANNEL_ID
import com.example.pixelsorting.NOTIFICATION_ID
import com.example.pixelsorting.NOTIFICATION_TITLE
import com.example.pixelsorting.OUTPUT_PATH
import com.example.pixelsorting.R
import com.example.pixelsorting.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.pixelsorting.VERBOSE_NOTIFICATION_CHANNEL_NAME
import com.example.pixelsorting.model.SortKey
import com.example.pixelsorting.model.SortType
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

private const val TAG = "WorkerUtils"

fun makeStatusNotification(message: String, context: Context) {

    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}

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

    // Определяем максимальное возможное значение для ключа сортировки
    val maxRange = when (sortKey) {
        SortKey.HUE -> 360.0
        else -> 255.0
    }
    // Порог, который определяет, насколько похожими должны быть значения соседних пикселей,
    // чтобы они были отсортированы вместе.
    val threshold = (level.toDouble() / 100) * maxRange

    when (sortType) {
        SortType.ROWS -> {
            for (y in 0 until height) {
                val rowPixels = MutableList(width) { x -> sortedBitmap.getPixel(x, y) }
                var segmentStart = 0
                var x = 0
                // Проходим по ряду, разбивая его на сегменты по порогу разницы
                while (x < width - 1) {
                    val currentValue = getKeyValue(rowPixels[x], sortKey)
                    val nextValue = getKeyValue(rowPixels[x + 1], sortKey)
                    if (kotlin.math.abs(nextValue - currentValue) > threshold) {
                        // Если в сегменте более одного пикселя – сортируем его
                        if ((x - segmentStart + 1) >= 2) {
                            val segment = rowPixels.subList(segmentStart, x + 1)
                            val sortedSegment = segment.sortedBy { pixel -> getKeyValue(pixel, sortKey) }
                            for (i in sortedSegment.indices) {
                                rowPixels[segmentStart + i] = sortedSegment[i]
                            }
                        }
                        segmentStart = x + 1
                    }
                    x++
                }
                // Сортируем последний сегмент, если он содержит более одного элемента
                if ((width - segmentStart) >= 2) {
                    val segment = rowPixels.subList(segmentStart, width)
                    val sortedSegment = segment.sortedBy { pixel -> getKeyValue(pixel, sortKey) }
                    for (i in sortedSegment.indices) {
                        rowPixels[segmentStart + i] = sortedSegment[i]
                    }
                }
                // Записываем отсортированный ряд обратно в изображение
                for (x in 0 until width) {
                    sortedBitmap.setPixel(x, y, rowPixels[x])
                }
            }
        }
        SortType.COLUMNS -> {
            for (x in 0 until width) {
                val colPixels = MutableList(height) { y -> sortedBitmap.getPixel(x, y) }
                var segmentStart = 0
                var y = 0
                while (y < height - 1) {
                    val currentVal = getKeyValue(colPixels[y], sortKey)
                    val nextVal = getKeyValue(colPixels[y + 1], sortKey)
                    if (kotlin.math.abs(nextVal - currentVal) > threshold) {
                        if ((y - segmentStart + 1) >= 2) {
                            val segment = colPixels.subList(segmentStart, y + 1)
                            val sortedSegment = segment.sortedBy { pixel -> getKeyValue(pixel, sortKey) }
                            for (i in sortedSegment.indices) {
                                colPixels[segmentStart + i] = sortedSegment[i]
                            }
                        }
                        segmentStart = y + 1
                    }
                    y++
                }
                if ((height - segmentStart) >= 2) {
                    val segment = colPixels.subList(segmentStart, height)
                    val sortedSegment = segment.sortedBy { pixel -> getKeyValue(pixel, sortKey) }
                    for (i in sortedSegment.indices) {
                        colPixels[segmentStart + i] = sortedSegment[i]
                    }
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
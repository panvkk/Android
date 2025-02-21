package com.example.notes.model

import android.media.audiofx.AudioEffect.Descriptor
import androidx.annotation.StringRes
import com.example.notes.R

data class Note (
    @StringRes val titleRes: Int,
    @StringRes val reminderTimeRes: Int,
    @StringRes val description: Int
)

fun getNotes() : List<Note> {
    return listOf(
        Note(titleRes = R.string.note_1, reminderTimeRes = R.string.time_1, description = R.string.desc_1),
        Note(titleRes = R.string.note_2, reminderTimeRes = R.string.time_2, description = R.string.desc_2),
        Note(titleRes = R.string.note_3, reminderTimeRes = R.string.time_3, description = R.string.desc_3),
        Note(titleRes = R.string.note_4, reminderTimeRes = R.string.time_4, description = R.string.desc_4),
        Note(titleRes = R.string.note_5, reminderTimeRes = R.string.time_5, description = R.string.desc_5),
        Note(titleRes = R.string.note_6, reminderTimeRes = R.string.time_6, description = R.string.desc_6),
        Note(titleRes = R.string.note_7, reminderTimeRes = R.string.time_7, description = R.string.desc_7),
        Note(titleRes = R.string.note_8, reminderTimeRes = R.string.time_8, description = R.string.desc_8),
        Note(titleRes = R.string.note_9, reminderTimeRes = R.string.time_9, description = R.string.desc_9),
        Note(titleRes = R.string.note_10, reminderTimeRes = R.string.time_10, description = R.string.desc_10)
    )
}
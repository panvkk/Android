package com.example.notes.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.notes.R

data class Note (
    @StringRes val titleRes: Int,
    @StringRes val reminderTimeRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int
)

fun getNotes() : List<Note> {
    return listOf(
        Note(titleRes = R.string.note_1, reminderTimeRes = R.string.time_1, descriptionRes = R.string.desc_1, imageRes = R.drawable.pic1),
        Note(titleRes = R.string.note_2, reminderTimeRes = R.string.time_2, descriptionRes = R.string.desc_2, imageRes = R.drawable.pic1),
        Note(titleRes = R.string.note_3, reminderTimeRes = R.string.time_3, descriptionRes = R.string.desc_3, imageRes = R.drawable.pic2),
        Note(titleRes = R.string.note_4, reminderTimeRes = R.string.time_4, descriptionRes = R.string.desc_4, imageRes = R.drawable.pic3),
        Note(titleRes = R.string.note_5, reminderTimeRes = R.string.time_5, descriptionRes = R.string.desc_5, imageRes = R.drawable.pic4),
        Note(titleRes = R.string.note_6, reminderTimeRes = R.string.time_6, descriptionRes = R.string.desc_6, imageRes = R.drawable.pic1),
        Note(titleRes = R.string.note_7, reminderTimeRes = R.string.time_7, descriptionRes = R.string.desc_7, imageRes = R.drawable.pic4),
        Note(titleRes = R.string.note_8, reminderTimeRes = R.string.time_8, descriptionRes = R.string.desc_8, imageRes = R.drawable.pic1),
        Note(titleRes = R.string.note_9, reminderTimeRes = R.string.time_9, descriptionRes = R.string.desc_9, imageRes = R.drawable.pic4),
        Note(titleRes = R.string.note_10, reminderTimeRes = R.string.time_10, descriptionRes = R.string.desc_10, imageRes = R.drawable.pic1)
    )
}
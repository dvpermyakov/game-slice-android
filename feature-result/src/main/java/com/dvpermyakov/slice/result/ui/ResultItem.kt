package com.dvpermyakov.slice.result.ui

import android.graphics.Bitmap

data class ResultItem(
    val image: Bitmap,
    val title: String,
    val description: String,
    val isDescriptionCrossed: Boolean,
    val extraDescription: String?,
    val isCorrect: Boolean
)
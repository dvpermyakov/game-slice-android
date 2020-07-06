package com.dvpermyakov.slice.result.ui

data class ResultItem(
    val image: String,
    val title: String,
    val description: String,
    val isDescriptionCrossed: Boolean,
    val extraDescription: String?,
    val isCorrect: Boolean
)
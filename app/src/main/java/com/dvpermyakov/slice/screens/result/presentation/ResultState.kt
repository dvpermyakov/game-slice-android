package com.dvpermyakov.slice.screens.result.presentation

import com.dvpermyakov.slice.screens.result.ui.ResultHeader
import com.dvpermyakov.slice.screens.result.ui.ResultItem

data class ResultState(
    val header: ResultHeader,
    val items: List<ResultItem>
)
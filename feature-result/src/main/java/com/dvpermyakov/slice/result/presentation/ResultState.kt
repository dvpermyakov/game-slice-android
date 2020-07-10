package com.dvpermyakov.slice.result.presentation

import com.dvpermyakov.slice.result.ui.ResultHeader
import com.dvpermyakov.slice.result.ui.ResultItem

data class ResultState(
    val header: ResultHeader,
    val items: List<ResultItem>
)
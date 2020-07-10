package com.dvpermyakov.slice.navigation

import androidx.navigation.NavDirections

interface Router {
    fun toResult(resultId: Long): NavDirections
}
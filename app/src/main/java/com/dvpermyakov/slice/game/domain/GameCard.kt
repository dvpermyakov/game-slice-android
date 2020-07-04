package com.dvpermyakov.slice.game.domain

import androidx.annotation.DrawableRes

data class GameCard(
    val name: String,
    @DrawableRes
    val image: Int
)
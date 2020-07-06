package com.dvpermyakov.slice.game.domain

data class GameResult(
    val id: Int,
    val cards: List<GameCardResult>
)
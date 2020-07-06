package com.dvpermyakov.slice.game.domain

data class GameResult(
    val id: Long,
    val cards: List<GameCardResult>
)
package com.dvpermyakov.slice.domain

data class GameResult(
    val id: Long,
    val cards: List<GameCardResult>
)
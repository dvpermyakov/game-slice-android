package com.dvpermyakov.slice.game.domain

data class Game(
    val title: String,
    val left: GameDeck,
    val right: GameDeck
)
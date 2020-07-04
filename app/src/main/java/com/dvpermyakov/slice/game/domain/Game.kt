package com.dvpermyakov.slice.game.domain

data class Game(
    val title: String,
    val left: GameDeck,
    val right: GameDeck
)

data class GameCard(
    val name: String,
    val image: String
)

data class GameDeck(
    val title: String,
    val cards: List<GameCard>
)
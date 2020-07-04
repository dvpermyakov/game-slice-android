package com.dvpermyakov.slice.game.presentation

import com.dvpermyakov.slice.game.domain.GameCard

sealed class GameState {
    data class NextCard(
        val title: String,
        val leftTitle: String,
        val rightTitle: String,
        val currentCard: GameCard,
        val nextCard: GameCard?
    ) : GameState()

    object GameEnd : GameState()
}
package com.dvpermyakov.slice.screens.game.presentation

import com.dvpermyakov.slice.domain.GameCard

sealed class GameState {
    data class NextCard(
        val title: String,
        val leftTitle: String,
        val rightTitle: String,
        val currentCard: GameCard,
        val nextCard: GameCard?
    ) : GameState()

    data class GameEnd(val resultId: Long) : GameState()
}
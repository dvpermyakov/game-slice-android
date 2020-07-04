package com.dvpermyakov.slice.game.presentation

import com.dvpermyakov.slice.game.domain.GameCard

sealed class GameState {
    object Loading : GameState()

    data class NextCard(
        val title: String,
        val leftTitle: String,
        val rightTitle: String,
        val currentCard: GameCard
    ) : GameState()
}
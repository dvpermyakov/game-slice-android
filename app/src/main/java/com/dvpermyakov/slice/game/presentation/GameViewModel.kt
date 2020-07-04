package com.dvpermyakov.slice.game.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvpermyakov.slice.game.domain.GameRepository

class GameViewModel(
    gameRepository: GameRepository
) : ViewModel() {

    private val gameState = MutableLiveData<GameState>()

    init {
        val game = gameRepository.getGame()
        gameState.postValue(
            GameState.NextCard(
                title = game.title,
                leftTitle = game.left.title,
                rightTitle = game.right.title,
                currentCard = game.left.cards.first()
            )
        )
    }

    fun getGameState(): LiveData<GameState> {
        return gameState
    }
}
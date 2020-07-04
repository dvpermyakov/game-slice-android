package com.dvpermyakov.slice.game.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvpermyakov.slice.game.domain.GameCard
import com.dvpermyakov.slice.game.domain.GameRepository

class GameViewModel(
    gameRepository: GameRepository
) : ViewModel() {

    private val gameState = MutableLiveData<GameState>()

    private val game = gameRepository.getGame()
    private val cards = getRandomCardOrder()
    private var currentIndex = 0

    init {
        gameState.postValue(getCurrentCardState())
    }

    fun getGameState(): LiveData<GameState> {
        return gameState
    }

    fun nextCard() {
        if (currentIndex < cards.lastIndex) {
            currentIndex++
            gameState.postValue(getCurrentCardState())
        } else {
            gameState.postValue(GameState.GameEnd)
        }
    }

    private fun getCurrentCardState() = GameState.NextCard(
        title = game.title,
        leftTitle = game.getLeftTitle(),
        rightTitle = game.getRightTitle(),
        currentCard = cards[currentIndex],
        nextCard = cards.getOrNull(currentIndex + 1)
    )

    private fun getRandomCardOrder(): List<GameCard> {
        return game.left.cards + game.right.cards
    }
}
package com.dvpermyakov.slice.game.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvpermyakov.slice.game.domain.GameCard
import com.dvpermyakov.slice.game.domain.GameCardResult
import com.dvpermyakov.slice.game.domain.GameRepository

class GameViewModel(
    gameRepository: GameRepository
) : ViewModel() {

    private val gameState = MutableLiveData<GameState>()

    private val game = gameRepository.getGame()
    private var cards: List<GameCard> = getRandomCardOrder()
    private var currentIndex = 0
    private val result: MutableList<GameCardResult> = mutableListOf()

    init {
        gameState.postValue(getCurrentCardState())
    }

    fun getGameState(): LiveData<GameState> {
        return gameState
    }

    fun chooseDeck(isLeft: Boolean) {
        val card = cards[currentIndex]
        result.add(
            GameCardResult(gameCard = card, isCorrect = game.isLeftForCard(card) == isLeft)
        )
        if (currentIndex < cards.lastIndex) {
            currentIndex++
            gameState.postValue(getCurrentCardState())
        } else {
            gameState.postValue(GameState.GameEnd)
        }
    }

    fun clear() {
        currentIndex = 0
        cards = getRandomCardOrder()
        gameState.postValue(getCurrentCardState())
    }

    private fun getCurrentCardState() = GameState.NextCard(
        title = game.title,
        leftTitle = game.getLeftTitle(),
        rightTitle = game.getRightTitle(),
        currentCard = cards[currentIndex],
        nextCard = cards.getOrNull(currentIndex + 1)
    )

    private fun getRandomCardOrder(): List<GameCard> {
        return (game.left.cards + game.right.cards).shuffled()
    }
}
package com.dvpermyakov.slice.screens.game.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvpermyakov.slice.domain.GameCard
import com.dvpermyakov.slice.domain.GameCardResult
import com.dvpermyakov.slice.domain.GameRepository
import com.dvpermyakov.slice.domain.GameResult

class GameViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val gameState = MutableLiveData<GameState>()

    private val game = gameRepository.getGame()
    private var cards = getRandomCardOrder()
    private var currentIndex = 0
    private val cardsResult = mutableListOf<GameCardResult>()

    init {
        gameState.postValue(getCurrentCardState())
    }

    fun getGameState(): LiveData<GameState> {
        return gameState
    }

    fun chooseDeck(isLeft: Boolean) {
        val card = cards[currentIndex]
        cardsResult.add(
            GameCardResult(gameCard = card, isCorrect = game.isLeftForCard(card) == isLeft)
        )
        if (currentIndex < cards.lastIndex) {
            currentIndex++
            gameState.postValue(getCurrentCardState())
        } else {
            val resultId = System.currentTimeMillis()
            gameRepository.saveGameResult(
                GameResult(
                    id = resultId,
                    cards = ArrayList(cardsResult)
                )
            )
            gameState.postValue(GameState.GameEnd(resultId))
        }
    }

    fun clear() {
        cards = getRandomCardOrder()
        currentIndex = 0
        cardsResult.clear()
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
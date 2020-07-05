package com.dvpermyakov.slice.result.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvpermyakov.slice.game.domain.GameCard
import com.dvpermyakov.slice.game.domain.GameRepository
import com.dvpermyakov.slice.result.ui.ResultHeader
import com.dvpermyakov.slice.result.ui.ResultItem

class ResultViewModel(
    gameRepository: GameRepository
) : ViewModel() {

    private val game = gameRepository.getGame()
    private val cards = getCardOrder()

    private val resultState = MutableLiveData<ResultState>()

    init {
        resultState.postValue(
            ResultState(
                items = cards.mapIndexed { index, card ->
                    ResultItem(
                        image = card.image,
                        title = card.name,
                        description = card.getDeckName(),
                        isDescriptionCrossed = index % 2 == 0,
                        extraDescription = if (index % 2 == 0) card.getDeckName() else null,
                        isRight = index % 2 != 0
                    )
                },
                header = ResultHeader(
                    title = game.title,
                    description = "10 / 12"
                )
            )
        )
    }

    fun getResultState(): LiveData<ResultState> {
        return resultState
    }

    fun onRetryClick() {
    }

    private fun getCardOrder(): List<GameCard> {
        return game.left.cards + game.right.cards
    }

    private fun GameCard.getDeckName(): String {
        return when {
            game.left.cards.any { card -> card.name == name } -> {
                game.left.title
            }
            game.right.cards.any { card -> card.name == name } -> {
                game.right.title
            }
            else -> {
                error("Didn't find deck for $name")
            }
        }
    }
}
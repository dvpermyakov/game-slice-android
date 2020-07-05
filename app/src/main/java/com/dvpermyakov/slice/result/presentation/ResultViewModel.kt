package com.dvpermyakov.slice.result.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvpermyakov.slice.game.domain.GameCard
import com.dvpermyakov.slice.game.domain.GameRepository
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
                items = cards.map { card ->
                    ResultItem(
                        image = card.image,
                        title = card.name,
                        description = "123"
                    )
                }
            )
        )
    }

    fun getResultState(): LiveData<ResultState> {
        return resultState
    }

    private fun getCardOrder(): List<GameCard> {
        return game.left.cards + game.right.cards
    }
}
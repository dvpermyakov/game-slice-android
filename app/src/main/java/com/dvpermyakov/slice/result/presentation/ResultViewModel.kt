package com.dvpermyakov.slice.result.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvpermyakov.slice.game.domain.GameRepository
import com.dvpermyakov.slice.result.ui.ResultHeader
import com.dvpermyakov.slice.result.ui.ResultItem

class ResultViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val game = gameRepository.getGame()
    private val resultState = MutableLiveData<ResultState>()

    fun setResultId(resultId: Long) {
        val result = gameRepository.getGameResult(resultId)
        resultState.postValue(
            ResultState(
                items = result.cards.map { cardResult ->
                    val card = cardResult.gameCard
                    val isCorrect = cardResult.isCorrect
                    val description =
                        if (isCorrect) {
                            game.getDeckNameForCard(card)
                        } else {
                            game.getOppositeDeckNameForCard(card)
                        }
                    val extraDescription =
                        if (isCorrect.not()) game.getDeckNameForCard(card) else null
                    ResultItem(
                        image = card.image,
                        title = card.name,
                        description = description,
                        isDescriptionCrossed = isCorrect.not(),
                        extraDescription = extraDescription,
                        isCorrect = isCorrect
                    )
                },
                header = ResultHeader(
                    title = game.title,
                    description = "${result.cards.sumBy { card -> if (card.isCorrect) 1 else 0 }} / ${result.cards.size}"
                )
            )
        )
    }

    fun getResultState(): LiveData<ResultState> {
        return resultState
    }

}
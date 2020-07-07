package com.dvpermyakov.slice.screens.result.presentation

import android.content.res.AssetManager
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvpermyakov.slice.domain.GameRepository
import com.dvpermyakov.slice.screens.result.ui.ResultHeader
import com.dvpermyakov.slice.screens.result.ui.ResultItem

class ResultViewModel(
    private val gameRepository: GameRepository,
    private val assetManager: AssetManager
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
                    val bitmap = BitmapFactory.decodeStream(assetManager.open(card.image))
                    ResultItem(
                        image = bitmap,
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
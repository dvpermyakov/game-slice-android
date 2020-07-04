package com.dvpermyakov.slice.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvpermyakov.slice.R
import com.dvpermyakov.slice.game.domain.GameCard

class GameViewModel : ViewModel() {
    private val gameState = MutableLiveData<GameState>().apply {
        postValue(
            GameState.NextCard(
                title = "123",
                leftTitle = "456",
                rightTitle = "5454",
                currentCard = GameCard("CCyjely", R.drawable.eqw)
            )
        )
    }

    fun getGameState(): LiveData<GameState> {
        return gameState
    }
}
package com.dvpermyakov.slice.game.domain

interface GameRepository {
    fun getGame(): Game
    fun saveGameResult(result: GameResult)
    fun getGameResult(id: Long): GameResult
}
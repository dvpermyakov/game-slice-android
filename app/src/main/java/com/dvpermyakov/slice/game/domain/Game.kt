package com.dvpermyakov.slice.game.domain

data class Game(
    val title: String,
    val left: GameDeck,
    val right: GameDeck
) {
    fun getLeftTitle(): String {
        return left.title
    }

    fun getRightTitle(): String {
        return right.title
    }

    fun getDeckNameForCard(card: GameCard): String {
        return when {
            left.cards.any { leftCard -> leftCard.name == card.name } -> {
                getLeftTitle()
            }
            right.cards.any { rightCard -> rightCard.name == card.name } -> {
                getRightTitle()
            }
            else -> {
                error("Didn't find deck for $card")
            }
        }
    }

    fun isLeftForCard(card: GameCard): Boolean {
        return when {
            left.cards.any { leftCard -> leftCard.name == card.name } -> {
                true
            }
            right.cards.any { rightCard -> rightCard.name == card.name } -> {
                false
            }
            else -> {
                error("Didn't find $card in the game")
            }
        }
    }
}

data class GameCard(
    val name: String,
    val image: String
)

data class GameDeck(
    val title: String,
    val cards: List<GameCard>
)
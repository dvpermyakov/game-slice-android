package com.dvpermyakov.slice.game.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
    @SerialName("game_title")
    val title: String,
    @SerialName("game_items")
    val items: List<GameDeckDto>
)

@Serializable
data class GameDeckDto(
    @SerialName("serial")
    val title: String,
    @SerialName("items")
    val items: List<GameCardDto>
)

@Serializable
data class GameCardDto(
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val image: String
)
package com.dvpermyakov.slice.game.data

import android.content.res.AssetManager
import android.util.Log
import com.dvpermyakov.slice.game.domain.Game
import com.dvpermyakov.slice.game.domain.GameCard
import com.dvpermyakov.slice.game.domain.GameDeck
import com.dvpermyakov.slice.game.domain.GameRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.IOException
import java.io.InputStream

class GameRepositoryImpl(
    private val assetManager: AssetManager
) : GameRepository {

    override fun getGame(): Game {
        val gameDto = assetManager.safetyRead(ASSET_FILE)?.let { jsonValue ->
            val json = Json(JsonConfiguration.Stable)
            json.parse(GameDto.serializer(), jsonValue)
        } ?: error("can't parse $ASSET_FILE")

        return Game(
            title = gameDto.title,
            left = gameDto.items[0].toDomain(),
            right = gameDto.items[1].toDomain()
        )
    }

    private fun GameDeckDto.toDomain() = GameDeck(
        title = this.title,
        cards = this.items.map { cardDto -> cardDto.toDomain() }
    )

    private fun GameCardDto.toDomain() = GameCard(
        name = this.name,
        image = String.format(ASSET_URI, this.image)
    )

    private fun AssetManager.safetyRead(path: String): String? {
        return try {
            open(path).safetyRead()
        } catch (ioe: IOException) {
            Log.e("AssetManager", "safetyRead", ioe)
            null
        }
    }

    private fun InputStream.safetyRead(): String? {
        return try {
            bufferedReader().use { it.readText() }
        } catch (ioe: IOException) {
            Log.e("InputStream", "safetyRead", ioe)
            null
        }
    }

    companion object {
        private const val ASSET_FILE = "game.json"
        private const val ASSET_URI = "file:///android_asset/%s.jpg"
    }
}
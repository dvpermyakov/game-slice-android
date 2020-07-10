package com.dvpermyakov.slice.navigation

import androidx.navigation.NavDirections
import com.dvpermyakov.slice.game.ui.GameFragmentDirections

class RouterImpl : Router {

    override fun toResult(resultId: Long): NavDirections {
        return GameFragmentDirections.actionShowResult(resultId)
    }

}
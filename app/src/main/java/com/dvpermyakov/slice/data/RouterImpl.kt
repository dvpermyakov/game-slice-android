package com.dvpermyakov.slice.data

import androidx.navigation.NavDirections
import com.dvpermyakov.slice.game.ui.GameFragmentDirections
import com.dvpermyakov.slice.navigation.Router

class RouterImpl : Router {

    override fun toResult(resultId: Long): NavDirections {
        return GameFragmentDirections.actionShowResult(resultId)
    }

}
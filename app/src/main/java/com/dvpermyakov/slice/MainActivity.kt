package com.dvpermyakov.slice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvpermyakov.slice.screens.game.ui.GameFragment
import com.dvpermyakov.slice.screens.result.ui.ResultFragment
import com.dvpermyakov.slice.router.MainRouter

class MainActivity : AppCompatActivity(), MainRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, GameFragment.newInstance())
                .commit()
        }
    }

    override fun showResult(resultId: Long) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
            .replace(R.id.container, ResultFragment.newInstance(resultId))
            .addToBackStack(null)
            .commit()
    }
}
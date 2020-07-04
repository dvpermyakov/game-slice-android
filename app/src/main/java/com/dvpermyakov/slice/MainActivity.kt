package com.dvpermyakov.slice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvpermyakov.slice.game.ui.GameFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, GameFragment.newInstance())
                .commit()
        }
    }
}
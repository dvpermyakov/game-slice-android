package com.dvpermyakov.slice

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.ViewModelProvider
import com.dvpermyakov.slice.game.data.GameRepositoryImpl
import com.dvpermyakov.slice.game.domain.GameRepository
import com.dvpermyakov.slice.game.presentation.GameViewModel
import com.dvpermyakov.slice.result.presentation.ResultViewModel
import com.dvpermyakov.slice.utils.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MainApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        bind<ViewModelProvider.Factory>() with singleton {
            ViewModelFactory(kodein.direct)
        }

        bind<AssetManager>() with singleton { this@MainApplication.applicationContext.assets }
        bind<GameRepository>() with singleton { GameRepositoryImpl(instance()) }
        bind<GameViewModel>(tag = GameViewModel::class.java.simpleName) with provider {
            GameViewModel(instance())
        }
        bind<ResultViewModel>(tag = ResultViewModel::class.java.simpleName) with provider {
            ResultViewModel(instance())
        }
    }
}
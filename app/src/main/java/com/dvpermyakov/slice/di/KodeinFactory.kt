package com.dvpermyakov.slice.di

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.ViewModelProvider
import com.dvpermyakov.slice.data.GameRepositoryImpl
import com.dvpermyakov.slice.navigation.RouterImpl
import com.dvpermyakov.slice.domain.GameRepository
import com.dvpermyakov.slice.game.presentation.GameViewModel
import com.dvpermyakov.slice.navigation.Router
import com.dvpermyakov.slice.result.presentation.ResultViewModel
import com.dvpermyakov.slice.utils.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

object KodeinFactory {

    fun create(application: Application): Kodein {
        return Kodein.lazy {
            bind<Router>() with singleton { RouterImpl() }
            bind<ViewModelProvider.Factory>() with singleton {
                ViewModelFactory(kodein.direct)
            }
            bind<AssetManager>() with singleton { application.applicationContext.assets }
            bind<GameRepository>() with singleton { GameRepositoryImpl(instance()) }
            bind<GameViewModel>(tag = GameViewModel::class.java.simpleName) with provider {
                GameViewModel(instance())
            }
            bind<ResultViewModel>(tag = ResultViewModel::class.java.simpleName) with provider {
                ResultViewModel(
                    instance(),
                    instance()
                )
            }
        }
    }

}
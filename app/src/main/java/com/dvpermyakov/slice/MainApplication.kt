package com.dvpermyakov.slice

import android.app.Application
import com.dvpermyakov.slice.di.KodeinFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class MainApplication : Application(), KodeinAware {
    override val kodein: Kodein = KodeinFactory.create(this)
}
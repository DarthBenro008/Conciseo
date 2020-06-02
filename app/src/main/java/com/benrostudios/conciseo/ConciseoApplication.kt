package com.benrostudios.conciseo

import android.app.Application
import com.benrostudios.conciseo.data.db.HistoryDatabase
import com.benrostudios.conciseo.data.network.ApiService
import com.benrostudios.conciseo.data.repository.ShortenRepository
import com.benrostudios.conciseo.data.repository.ShortenRepositoryImpl
import com.benrostudios.conciseo.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ConciseoApplication: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ConciseoApplication))

        bind() from singleton { HistoryDatabase(instance()) }
        bind() from singleton { instance<HistoryDatabase>().historyDAO() }
        bind() from singleton { ApiService() }
        bind<ShortenRepository>() with singleton { ShortenRepositoryImpl(instance(),instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }

    }
}
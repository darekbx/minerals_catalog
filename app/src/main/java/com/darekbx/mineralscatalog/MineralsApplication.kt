package com.darekbx.mineralscatalog

import android.app.Application
import com.darekbx.mineralscatalog.di.appModule
import com.darekbx.mineralscatalog.di.domainModule
import com.darekbx.mineralscatalog.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MineralsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MineralsApplication)
            modules(appModule, domainModule, viewModelModule)
        }
    }
}
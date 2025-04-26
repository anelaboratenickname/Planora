package ru.zalesov.planora

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.zalesov.planora.data.di.DataModule
import ru.zalesov.planora.features.home.di.HomeModule

class PlanoraApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PlanoraApplication)
            modules(DataModule, HomeModule)
        }
    }

}
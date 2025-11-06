package com.asistentedelmago

import android.app.Application
import com.asistentedelmago.core.data.di.dataModule
import com.asistentedelmago.feature.repertory.di.repertoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AsistenteDelMagoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AsistenteDelMagoApplication)
            modules(dataModule, repertoryModule)
        }
    }
}


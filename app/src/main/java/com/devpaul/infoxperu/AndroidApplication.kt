package com.devpaul.infoxperu

import android.app.Application
import com.devpaul.core_platform.log.TimberFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module
class AndroidApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        koinInitializer()
        timberInitializer()
    }

    private fun koinInitializer() = startKoin {
        androidContext(this@AndroidApplication)
        androidLogger()
        modules(AppModule().module)
    }

    private fun timberInitializer() {
        TimberFactory.setup()
    }
}
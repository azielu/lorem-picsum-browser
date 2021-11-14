package com.azielu.lorempicsumbrowser

import android.app.Application
import com.azielu.lorempicsumbrowser.di.modules.applicationModule
import com.azielu.lorempicsumbrowser.di.modules.useCaseModule
import com.azielu.lorempicsumbrowser.di.modules.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

internal open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(modules)
        }
    }

    protected open val modules: List<Module> = listOf(
        applicationModule,
        viewModule,
        useCaseModule,
    )
}

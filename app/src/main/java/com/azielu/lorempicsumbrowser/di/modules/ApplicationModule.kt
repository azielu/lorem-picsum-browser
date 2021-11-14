package com.azielu.lorempicsumbrowser.di.modules

import com.azielu.lorempicsumbrowser.api.ApiInterface
import com.azielu.lorempicsumbrowser.repository.ImagesRepository
import com.azielu.lorempicsumbrowser.repository.ImagesRepositoryImpl
import com.azielu.lorempicsumbrowser.util.Cache
import com.azielu.lorempicsumbrowser.util.SharedPreferencesCache
import org.koin.dsl.module

internal val applicationModule = module {

    single<Cache> {
        SharedPreferencesCache(
            context = get()
        )
    }

    single<ApiInterface> {
        ApiInterface.create()
    }

    single<ImagesRepository> {
        ImagesRepositoryImpl(
            cache = get(),
            api = get()
        )
    }
}

package com.azielu.lorempicsumbrowser.di.modules

import com.azielu.lorempicsumbrowser.usecase.FetchPhotosUseCase
import org.koin.dsl.module

internal val useCaseModule = module {

    factory<FetchPhotosUseCase> {
        FetchPhotosUseCase(
            repository = get()
        )
    }
}

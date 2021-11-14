package com.azielu.lorempicsumbrowser.di.modules

import com.azielu.lorempicsumbrowser.ui.images.ImagesContract
import com.azielu.lorempicsumbrowser.ui.images.ImagesPresenter
import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

internal val viewModule = module {

    factory { CompositeDisposable() }

    factory<ImagesContract.Presenter> {
        ImagesPresenter(
            compositeDisposable = get(),
            fetchPhotosUseCase = get()
        )
    }
}

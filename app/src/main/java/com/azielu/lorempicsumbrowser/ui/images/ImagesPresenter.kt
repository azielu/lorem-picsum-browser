package com.azielu.lorempicsumbrowser.ui.images

import com.azielu.lorempicsumbrowser.model.TempGlobalImages
import com.azielu.lorempicsumbrowser.mvp.BasePresenter
import io.reactivex.disposables.CompositeDisposable

class ImagesPresenter(compositeDisposable: CompositeDisposable?) :
    BasePresenter<ImagesContract.View>(compositeDisposable),
    ImagesContract.Presenter {

    private var lastImageIndex = 0

    override fun fetchImages() {
        val images = TempGlobalImages.take(lastImageIndex + NEW_IMAGES_PER_QUERY)
        lastImageIndex += NEW_IMAGES_PER_QUERY
        view?.loadImages(images)
    }

    companion object {
        const val NEW_IMAGES_PER_QUERY = 10;
    }
}

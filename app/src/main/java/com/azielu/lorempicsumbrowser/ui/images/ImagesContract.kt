package com.azielu.lorempicsumbrowser.ui.images

import com.azielu.lorempicsumbrowser.model.ImageData
import com.azielu.lorempicsumbrowser.mvp.MvpPresenter
import com.azielu.lorempicsumbrowser.mvp.MvpView

object ImagesContract {
    interface View : MvpView {
        fun loadImages(images : List<ImageData>)
        fun showError(error: Throwable)
    }

    interface Presenter : MvpPresenter<View> {
        fun fetchMoreImages()
        fun fetchFirstImages()
    }
}

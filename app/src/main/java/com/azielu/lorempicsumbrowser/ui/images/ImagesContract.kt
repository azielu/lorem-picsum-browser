package com.azielu.lorempicsumbrowser.ui.images

import com.azielu.lorempicsumbrowser.model.ImageData
import com.azielu.lorempicsumbrowser.mvp.MvpPresenter
import com.azielu.lorempicsumbrowser.mvp.MvpView

object ImagesContract {
    interface View : MvpView {
        fun loadImages(images : List<ImageData>)
    }

    interface Presenter : MvpPresenter<View> {
        fun fetchImages()
    }
}

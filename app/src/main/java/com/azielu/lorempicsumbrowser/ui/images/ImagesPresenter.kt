package com.azielu.lorempicsumbrowser.ui.images

import com.azielu.lorempicsumbrowser.mvp.BasePresenter
import com.azielu.lorempicsumbrowser.usecase.FetchPhotosUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ImagesPresenter(
    compositeDisposable: CompositeDisposable?,
    private val fetchPhotosUseCase: FetchPhotosUseCase
) :
    BasePresenter<ImagesContract.View>(compositeDisposable),
    ImagesContract.Presenter {

    private var currentPage = 0

    override fun fetchMoreImages() {
        disposable {
            fetchPhotosUseCase.execute(currentPage)
                .subscribeOn(Schedulers.io())
                .doOnSuccess { currentPage++ }
                .observeOn(AndroidSchedulers.mainThread())
                //TODO handle error
                .subscribe { list, error ->
                    list?.let {
                        view?.loadImages(list)
                    }
                }
        }
    }

    override fun fetchFirstImages() {
        currentPage = 0
        fetchMoreImages()
    }
}

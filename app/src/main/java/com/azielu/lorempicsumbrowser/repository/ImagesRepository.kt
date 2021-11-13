package com.azielu.lorempicsumbrowser.repository

import android.annotation.SuppressLint
import com.azielu.lorempicsumbrowser.api.ApiInterface
import com.azielu.lorempicsumbrowser.model.ImageData
import com.azielu.lorempicsumbrowser.util.Cache
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.ObservableEmitter


interface ImagesRepository {
    fun getImages(page: Int): Single<List<ImageData>>
}

internal class ImagesRepositoryImpl(private val cache: Cache) : ImagesRepository {

    override fun getImages(page: Int): Single<List<ImageData>> {
        return Single.fromObservable(
            getPageFromCacheObservable(page)
                .concatWith(getRealPageObservable(page)
                    .doOnNext { list ->
                        list?.let { cache.saveToCache(page.toString(), list) }
                    })
                .take(1)
        )
    }

    private fun getRealPageObservable(page: Int): Observable<List<ImageData>> {
        //LoremPicsum iterates pages from 1
        return ApiInterface.create().getImages(page + 1)
            .map { list ->
                list.map {
                    ImageData(it.id, it.author)
                }
            }
    }

    @SuppressLint("CheckResult")
    private fun getPageFromCacheObservable(page: Int): Observable<List<ImageData>> {
        return Observable.create { e: ObservableEmitter<List<ImageData>> ->
            val result: List<ImageData>? = cache.getFromCache(page.toString())
            if (result != null) {
                e.onNext(result)
            }
            e.onComplete()
        }
    }
}

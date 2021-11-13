package com.azielu.lorempicsumbrowser.usecase

import com.azielu.lorempicsumbrowser.model.ImageData
import com.azielu.lorempicsumbrowser.repository.ImagesRepository
import io.reactivex.Single

class FetchPhotosUseCase(private val repository: ImagesRepository) {
    fun execute(page: Int): Single<List<ImageData>> {
        return repository.getImages(page)
    }
}

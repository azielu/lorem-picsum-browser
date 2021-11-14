package com.azielu.lorempicsumbrowser.ui.images

import com.azielu.lorempicsumbrowser.BaseTest
import com.azielu.lorempicsumbrowser.model.ImageData
import com.azielu.lorempicsumbrowser.usecase.FetchPhotosUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Test

class ImagesPresenterTest : BaseTest() {

    private val view = mockk<ImagesContract.View>(relaxed = true)
    private val useCase = mockk<FetchPhotosUseCase>()

    private lateinit var presenter: ImagesPresenter

    @Before
    fun setUp() {
        presenter = ImagesPresenter(CompositeDisposable(), useCase)
        presenter.initialize(view)
    }

    @Test
    fun `presenter makes view handle fetching error`() {
        every { useCase.execute(any()) } returns Single.error(Exception("error in Observable"))
        presenter.fetchFirstImages()

        verify { view.showError(any()) }
        verify(exactly = 0) { view.loadImages(any()) }
    }

    @Test
    fun `presenter makes view loads images after fetching first page of images`() {
        every { useCase.execute(0) } returns Single.just(PAGE_1)
        presenter.fetchFirstImages()

        verify { view.loadImages(PAGE_1) }
        verify(exactly = 0) { view.showError(any()) }
    }

    @Test
    fun `presenter makes view loads all images after fetching more pages after first one`() {
        every { useCase.execute(0) } returns Single.just(PAGE_0)
        every { useCase.execute(1) } returns Single.just(PAGE_1)
        every { useCase.execute(2) } returns Single.just(PAGE_2)

        presenter.fetchFirstImages()
        verify(exactly = 1) { view.loadImages(PAGE_0) }

        presenter.fetchMoreImages()
        verify(exactly = 1) { view.loadImages(PAGE_1) }

        presenter.fetchMoreImages()
        verify(exactly = 1) { view.loadImages(PAGE_2) }

        verify(exactly = 0) { view.showError(any()) }
    }

    companion object {
        val PAGE_0 = listOf(
            ImageData(1, "Author_1"),
            ImageData(2, "Author_2"),
            ImageData(3, "Author_3")
        )
        val PAGE_1 = listOf(
            ImageData(4, "Author_4"),
            ImageData(5, "Author_5"),
            ImageData(6, "Author_6")
        )
        val PAGE_2 = listOf(
            ImageData(7, "Author_7"),
            ImageData(8, "Author_8"),
            ImageData(9, "Author_9")
        )
    }
}

package com.azielu.lorempicsumbrowser.usecase

import com.azielu.lorempicsumbrowser.model.ImageData
import com.azielu.lorempicsumbrowser.repository.ImagesRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FetchPhotosUseCaseTest {

    private val repository = mockk<ImagesRepository>()

    private lateinit var useCase: FetchPhotosUseCase

    @Before
    fun setUp() {
        useCase = FetchPhotosUseCase(repository)
    }

    @Test
    fun `fetching images throws error`() {
        every { repository.getImages(any()) } returns Single.error(Exception("error in Observable"))

        val actual = useCase.execute(1).test()
        actual.assertError(Exception::class.java)
    }

    @Test
    fun `usecase fetches images`() {
        every { repository.getImages(1) } returns Single.just(PAGE_1)
        every { repository.getImages(2) } returns Single.just(PAGE_2)

        val actual1 = useCase.execute(1).test()
        val actual2 = useCase.execute(2).test()

        actual1.assertValue{
            assertNotNull(it)
            assertEquals(it.count(), PAGE_1.count())
            PAGE_1.forEachIndexed { i, item ->
                assertEquals(item, it[i])
            }
            return@assertValue true
        }

        actual2.assertValue{
            assertNotNull(it)
            assertEquals(it.count(), PAGE_2.count())
            PAGE_2.forEachIndexed { i, item ->
                assertEquals(item, it[i])
            }
            return@assertValue true
        }
    }

    companion object {
        val PAGE_1 = listOf(
            ImageData(1, "Author_1"),
            ImageData(2, "Author_2"),
            ImageData(3, "Author_3")
        )
        val PAGE_2 = listOf(
            ImageData(4, "Author_4"),
            ImageData(5, "Author_5"),
            ImageData(6, "Author_6")
        )
    }
}

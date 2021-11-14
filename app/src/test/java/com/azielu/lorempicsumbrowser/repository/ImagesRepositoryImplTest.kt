package com.azielu.lorempicsumbrowser.repository

import com.azielu.lorempicsumbrowser.BaseTest
import com.azielu.lorempicsumbrowser.api.ApiInterface
import com.azielu.lorempicsumbrowser.api.model.RawImageData
import com.azielu.lorempicsumbrowser.model.ImageData
import com.azielu.lorempicsumbrowser.util.Cache
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ImagesRepositoryImplTest : BaseTest() {

    private val cache = mockk<Cache>(relaxed = true)
    private val api = mockk<ApiInterface>(relaxed = true)

    private lateinit var repository: ImagesRepository

    @Before
    fun setUp() {
        repository = ImagesRepositoryImpl(api, cache)
    }

    @Test
    fun `repository returns cached pages`() {
        every { cache.getFromCache(PAGE_0_KEY) } returns CACHED_PAGE_0
        every { cache.getFromCache(PAGE_1_KEY) } returns CACHED_PAGE_1

        var actual = repository.getImages(0).test()
        actual.assertValue {
            Assert.assertNotNull(it)
            Assert.assertEquals(it.count(), CACHED_PAGE_0.count())
            CACHED_PAGE_0.forEachIndexed { i, item ->
                Assert.assertEquals(item, it[i])
            }
            return@assertValue true
        }

        actual = repository.getImages(1).test()
        actual.assertValue {
            Assert.assertNotNull(it)
            Assert.assertEquals(it.count(), CACHED_PAGE_1.count())
            CACHED_PAGE_1.forEachIndexed { i, item ->
                Assert.assertEquals(item, it[i])
            }
            return@assertValue true
        }
    }

    @Test
    fun `repository returns pages from api when not cached`() {
        every { cache.getFromCache(PAGE_0_KEY) } returns null
        every { cache.getFromCache(PAGE_1_KEY) } returns null
        //LoremPicsum iterates pages from 1
        every { api.getImages(1) } returns Observable.just(RAW_PAGE_0)
        every { api.getImages(2) } returns Observable.just(RAW_PAGE_1)

        var actual = repository.getImages(0).test()
        actual.assertValue {
            Assert.assertNotNull(it)
            Assert.assertEquals(it.count(), RAW_PAGE_0.count())
            RAW_PAGE_0.forEachIndexed { i, item ->
                Assert.assertEquals(
                    ImageData(item.id, item.author),
                    ImageData(it[i].id, it[i].author)
                )
            }
            return@assertValue true
        }

        actual = repository.getImages(1).test()
        actual.assertValue {
            Assert.assertNotNull(it)
            Assert.assertEquals(it.count(), RAW_PAGE_1.count())
            RAW_PAGE_1.forEachIndexed { i, item ->
                Assert.assertEquals(
                    ImageData(item.id, item.author),
                    ImageData(it[i].id, it[i].author)
                )
            }
            return@assertValue true
        }
    }

    @Test
    fun `repository caches pages`() {
        every { cache.getFromCache(PAGE_0_KEY) } returns null
        every { cache.getFromCache(PAGE_1_KEY) } returns null
        //LoremPicsum iterates pages from 1
        every { api.getImages(1) } returns Observable.just(RAW_PAGE_0)
        every { api.getImages(2) } returns Observable.just(RAW_PAGE_1)

        repository.getImages(0).test()
        repository.getImages(1).test()

        verify(exactly = 1) {
            cache.saveToCache(
                PAGE_0_KEY,
                RAW_PAGE_0.map { ImageData(it.id, it.author) })
        }
        verify(exactly = 1) {
            cache.saveToCache(
                PAGE_1_KEY,
                RAW_PAGE_1.map { ImageData(it.id, it.author) })
        }
    }

    @Test
    fun `fetching images from api throws error`() {
        every { cache.getFromCache(any()) } returns null
        every { api.getImages(any()) } returns Observable.error(Exception("error in Observable"))

        val actual = repository.getImages(0).test()
        actual.assertError(Exception::class.java)
    }

    companion object {
        const val PAGE_0_KEY = "0"
        const val PAGE_1_KEY = "1"

        val RAW_PAGE_0 = listOf(
            RawImageData(1, "Author_1", 0, 0, "", ""),
            RawImageData(2, "Author_2", 0, 0, "", ""),
            RawImageData(3, "Author_3", 0, 0, "", "")
        )
        val CACHED_PAGE_0 = listOf(
            ImageData(1, "Author_1_Cached"),
            ImageData(2, "Author_2_Cached"),
            ImageData(3, "Author_3_Cached")
        )
        val RAW_PAGE_1 = listOf(
            RawImageData(4, "Author_4", 0, 0, "", ""),
            RawImageData(5, "Author_5", 0, 0, "", ""),
            RawImageData(6, "Author_6", 0, 0, "", "")
        )
        val CACHED_PAGE_1 = listOf(
            ImageData(4, "Author_4_Cached"),
            ImageData(5, "Author_5_Cached"),
            ImageData(6, "Author_6_Cached")
        )
    }
}


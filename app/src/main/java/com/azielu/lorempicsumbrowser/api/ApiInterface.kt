package com.azielu.lorempicsumbrowser.api

import com.azielu.lorempicsumbrowser.api.model.RawImageData
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("list")
    fun getImages(
        @Query("page") page: Int,
    ): Observable<List<RawImageData>>

    companion object {

        var BASE_URL = "https://picsum.photos/v2/"

        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}

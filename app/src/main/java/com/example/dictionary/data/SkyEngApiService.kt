package com.example.dictionary.data

import com.example.dictionary.domain.WordData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SkyEngApiService {

    @GET("api/public/v1/words/search")
    fun listWordData(@Query("search") word: String): Single<List<WordData>>
}
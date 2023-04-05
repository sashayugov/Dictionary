package com.example.dictionary.data.retrofit

import com.example.dictionary.domain.WordData
import com.example.dictionary.domain.entity.WordDataModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SkyEngApiService {

    @GET("api/public/v1/words/search")
    fun listWordData(@Query("search") word: String): Single<List<WordDataModel>>
}
package com.example.dictionary.data

import com.example.dictionary.domain.DictionaryContract
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://dictionary.skyeng.ru/"

class RepositoryImpl : DictionaryContract.Repository {

    fun getData(): SkyEngApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(SkyEngApiService::class.java)
    }

}
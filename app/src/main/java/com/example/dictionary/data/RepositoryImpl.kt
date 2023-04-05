package com.example.dictionary.data

import com.example.dictionary.data.retrofit.SkyEngApiService
import com.example.dictionary.DictionaryContract
import com.example.dictionary.domain.entity.WordDataModel
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://dictionary.skyeng.ru/"

class RepositoryImpl : DictionaryContract.Repository<List<WordDataModel>> {

     private val api: SkyEngApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(SkyEngApiService::class.java)

    override fun getDataByWord(word: String): Single<List<WordDataModel>> {
        return  api.listWordData(word)
    }
}
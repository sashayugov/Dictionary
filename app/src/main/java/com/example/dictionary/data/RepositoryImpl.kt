package com.example.dictionary.data

import com.example.dictionary.data.retrofit.SkyEngApiService
import com.example.dictionary.DictionaryContract
import com.example.dictionary.domain.entity.WordDataModel
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private val api: SkyEngApiService) : DictionaryContract.Repository {

    override fun getDataByWord(word: String): Single<List<WordDataModel>> {
        return  api.listWordData(word)
    }
}
package com.example.dictionary.di

import com.example.dictionary.DictionaryContract
import com.example.dictionary.data.RepositoryImpl
import com.example.dictionary.data.retrofit.SkyEngApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, AppBindModule::class])
class AppModule

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesApi(): SkyEngApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(SkyEngApiService::class.java)
    }

    companion object {
        private const val BASE_URL = "https://dictionary.skyeng.ru/"
    }
}

@Module
interface AppBindModule {

    @Singleton
    @Binds
    fun providesRepositoryToRepositoryImpl (repositoryImpl: RepositoryImpl): DictionaryContract.Repository
}


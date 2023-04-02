package com.example.dictionary

import android.app.Application
import com.example.dictionary.data.RepositoryImpl
import com.example.dictionary.domain.DictionaryContract
import com.example.dictionary.presenter.MainPresenter

class App : Application() {

    lateinit var mainPresenter: MainPresenter

    override fun onCreate() {
        super.onCreate()
        instance = this
        mainPresenter = MainPresenter(RepositoryImpl())
    }

    companion object {
        lateinit var instance: App
        private set
    }
}

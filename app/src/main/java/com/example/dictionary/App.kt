package com.example.dictionary

import android.app.Application
import android.content.Context
import com.example.dictionary.presenter.MainPresenter

class App : Application() {

    lateinit var mainPresenter: MainPresenter

    override fun onCreate() {
        super.onCreate()
        instance = this
        mainPresenter = MainPresenter()
    }

    companion object {
        lateinit var instance: App
        private set
    }
}

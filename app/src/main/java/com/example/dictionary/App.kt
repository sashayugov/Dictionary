package com.example.dictionary

import android.app.Application
import com.example.dictionary.data.RepositoryImpl

class App : Application() {

    lateinit var repo: RepositoryImpl

    override fun onCreate() {
        super.onCreate()
        instance = this
        repo = RepositoryImpl()
    }

    companion object {
        lateinit var instance: App
        private set
    }
}

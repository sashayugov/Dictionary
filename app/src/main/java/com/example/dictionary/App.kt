package com.example.dictionary

import android.app.Application
import android.content.Context
import com.example.dictionary.di.AppComponent
import com.example.dictionary.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.create()
        super.onCreate()
    }
}

val Context.app
    get() = applicationContext as App

package com.example.dictionary

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Single

interface DictionaryContract {

    interface View<T> {
        fun renderData(wordData: T?)
    }

    interface Presenter<T> {
        fun attach(view: View<T>)
        fun onLoadDataByWord(word: String)
        fun detach()
    }

    interface ViewModel<T> {
        fun onLoadDataByWord(word: String)
    }

    interface Repository<T> {
        fun getDataByWord(word: String): Single<T>
    }
}
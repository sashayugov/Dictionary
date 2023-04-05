package com.example.dictionary.domain

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

    interface Repository<T> {
        fun getDataByWord(word: String): Single<T>
    }
}
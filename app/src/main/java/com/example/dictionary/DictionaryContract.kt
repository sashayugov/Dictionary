package com.example.dictionary

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Single

interface DictionaryContract {

    interface View {
        fun renderData()
    }

    interface ViewModel {
        fun onLoadDataByWord(word: String)
    }

    interface Repository<T> {
        fun getDataByWord(word: String): Single<T>
    }
}
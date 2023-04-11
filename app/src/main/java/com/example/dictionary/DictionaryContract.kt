package com.example.dictionary

import com.example.dictionary.domain.entity.WordDataModel
import io.reactivex.rxjava3.core.Single

interface DictionaryContract {

    interface View {
        fun renderData()
    }

    interface ViewModel {
        fun onLoadDataByWord(word: String)
    }

    interface Repository {
        fun getDataByWord(word: String): Single<List<WordDataModel>>
    }
}
package com.example.dictionary

interface DictionaryContract {

    interface View {
        fun renderWordData()
    }

    interface Presenter {
        fun attach(view: View)
        fun loadDataByWord(word: String)
        fun detach()
    }

    interface Repository
}
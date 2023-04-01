package com.example.dictionary.domain

interface DictionaryContract {

    interface View {
        fun openSearchFragment()
        fun openWordsListFragment()
    }

    interface Presenter {
        fun attach(view: View)
        fun onLoadDataByWord(word: String)
        fun detach()
    }

    interface Repository
}
package com.example.dictionary.domain

interface DictionaryContract {

    interface View {
        fun openSearchFragment()
        fun openWordListFragment()
    }

    interface Presenter<T> {
        val wordData: T
        fun attach(view: View)
        fun onLoadDataByWord(word: String)
        fun detach()
    }

    interface Repository<T> {
       val api: T
    }
}
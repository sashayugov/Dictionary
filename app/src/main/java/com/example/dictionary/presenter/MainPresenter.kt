package com.example.dictionary.presenter

import com.example.dictionary.domain.DictionaryContract

class MainPresenter(private val wordRepository: DictionaryContract.Repository) :
    DictionaryContract.Presenter {

    private var activity: DictionaryContract.View? = null

    override fun attach(view: DictionaryContract.View) {
        this.activity = view
        activity?.openSearchFragment()
    }

    override fun detach() {
        activity = null
    }

    override fun onLoadDataByWord(word: String) {
        activity?.openWordsListFragment()
    }
}
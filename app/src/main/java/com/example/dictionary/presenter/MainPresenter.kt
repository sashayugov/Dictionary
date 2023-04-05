package com.example.dictionary.presenter

import com.example.dictionary.domain.DictionaryContract
import com.example.dictionary.domain.WordData
import com.example.dictionary.domain.entity.WordDataModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter(
    private val wordRepository: DictionaryContract.Repository<List<WordDataModel>>,
) :
    DictionaryContract.Presenter<WordData> {

    private var activity: DictionaryContract.View<WordData>? = null
    private var disposableWordsList: Disposable? = null

    var wordData: WordData? = null
        private set

    override fun attach(view: DictionaryContract.View<WordData>) {
        this.activity = view
        activity?.renderData(wordData)
    }

    override fun detach() {
        activity = null
        disposableWordsList?.dispose()
    }

    override fun onLoadDataByWord(word: String) {
        disposableWordsList = wordRepository.getDataByWord(word)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                wordData = WordData.Loading(1)
                activity?.renderData(wordData as WordData.Loading) }
            .subscribeBy(
                onSuccess = { listDataModel ->
                    wordData = WordData.Success(listDataModel)
                    activity?.renderData(wordData as WordData.Success)
                },
                onError = { throwable ->
                    wordData = WordData.Error(throwable)
                    activity?.renderData(wordData as WordData.Error)
                }
            )
    }
}
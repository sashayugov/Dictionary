package com.example.dictionary.presenter

import com.example.dictionary.data.SkyEngApiService
import com.example.dictionary.domain.DictionaryContract
import com.example.dictionary.domain.WordData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter(private val wordRepository: DictionaryContract.Repository<SkyEngApiService>) :
    DictionaryContract.Presenter<WordData> {

    private var activity: DictionaryContract.View? = null
    private var disposableWordsList: Disposable? = null

    override var wordData: WordData = WordData.Loading(0)
        private set

    override fun attach(view: DictionaryContract.View) {
        this.activity = view
        activity?.openSearchFragment()
    }

    override fun detach() {
        activity = null
        disposableWordsList?.dispose()
    }

    override fun onLoadDataByWord(word: String) {
        activity?.openWordListFragment()
        disposableWordsList = wordRepository.api.listWordData(word)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { listDataModel ->
                    Thread.sleep(3000)
                    wordData = WordData.Success(listDataModel)
                    activity?.openWordListFragment()
                },
                onError = { throwable ->
                    wordData = WordData.Error(throwable)
                    activity?.openWordListFragment()
                }
            )
    }
}
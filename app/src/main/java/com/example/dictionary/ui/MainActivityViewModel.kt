package com.example.dictionary.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionary.DictionaryContract
import com.example.dictionary.domain.WordData
import com.example.dictionary.domain.entity.WordDataModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityViewModel(
    private val wordRepository: DictionaryContract.Repository<List<WordDataModel>>,
) : ViewModel(),
    DictionaryContract.ViewModel<WordData> {

    private val _liveWordData: MutableLiveData<WordData> = MutableLiveData()
    val liveWordData: LiveData<WordData> = _liveWordData
    private var disposableWordsList: Disposable? = null

    override fun onLoadDataByWord(word: String) {
        disposableWordsList = wordRepository.getDataByWord(word)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _liveWordData.value = WordData.Loading(0) }
            .subscribeBy(
                onSuccess = { listDataModel ->
                    _liveWordData.value = WordData.Success(listDataModel)
                },
                onError = { throwable ->
                    _liveWordData.value = WordData.Error(throwable)
                }
            )
    }

    override fun onCleared() {
        disposableWordsList?.dispose()
        super.onCleared()
    }
}
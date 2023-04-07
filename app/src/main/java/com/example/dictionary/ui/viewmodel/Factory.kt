package com.example.dictionary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dictionary.DictionaryContract
import com.example.dictionary.domain.entity.WordDataModel

class Factory(private val repo: DictionaryContract.Repository<List<WordDataModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repo) as T
    }
}
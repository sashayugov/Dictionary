package com.example.dictionary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dictionary.DictionaryContract
import com.example.dictionary.domain.entity.WordDataModel
import javax.inject.Inject

class Factory @Inject constructor (private val repo: DictionaryContract.Repository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == MainActivityViewModel::class.java)
        return MainActivityViewModel(repo) as T
    }
}
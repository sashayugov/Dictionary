package com.example.dictionary.domain

import com.example.dictionary.domain.entity.WordDataModel

sealed class WordData {

    data class Success(val wordData: WordDataModel) : WordData()

    data class Error(val error: Throwable) : WordData()

    data class Loading(val progress: Int) : WordData()
}


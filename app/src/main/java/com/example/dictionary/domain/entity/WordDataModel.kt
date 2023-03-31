package com.example.dictionary.domain.entity

import com.google.gson.annotations.SerializedName

data class WordDataModel(
    @SerializedName("text")
    val text: String,
    @SerializedName("meanings")
    val meanings: List<Meanings>
)

data class Meanings(
    @SerializedName("translation")
    val translation: Translation,
    @SerializedName("imageUrl")
    val imageUrl: String?
)

data class Translation(
    @SerializedName("text")
    val text: String,
    @SerializedName("note")
    val note: String?
)

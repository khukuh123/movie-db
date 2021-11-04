package com.miko.moviedb.presentation.model.detail

data class DetailModel(
    val id: Int,
    val title: String,
    val duration: Int,
    val genres: ArrayList<String>,
    var isFavorite: Boolean,
    val synopsis: String,
    val casts: ArrayList<ItemCastModel>,
    val backDrop: String
)

package com.miko.moviedb.presentation.model.favorite

data class ItemFavoriteModel(
    val id: Int,
    val title: String,
    val year: String,
    val genres: ArrayList<String>,
    val poster: String,
    var isFavorite: Boolean
)
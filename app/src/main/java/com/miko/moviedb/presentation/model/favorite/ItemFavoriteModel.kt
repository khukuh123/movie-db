package com.miko.moviedb.presentation.model.favorite

data class ItemFavoriteModel(
    val id: Int,
    val title: String,
    val year: Int,
    val genres: ArrayList<String>,
    val poster: String,
    val isFavorite: Boolean
)
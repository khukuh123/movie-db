package com.miko.moviedb.presentation.model.popular

data class ItemPopularModel(
    val id: Int,
    val title: String,
    val genre: String,
    val overview: String,
    val poster: String
)

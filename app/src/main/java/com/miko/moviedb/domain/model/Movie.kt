package com.miko.moviedb.domain.model

import com.miko.moviedb.presentation.model.detail.ItemCastModel

data class Movie(
    val id: Int,
    val title: String,
    val duration: Int,
    val year: String,
    val genres: ArrayList<String>,
    val isFavorite: Boolean,
    val synopsis: String,
    val casts: ArrayList<ItemCastModel>,
    val poster: String,
    val backDrop: String
)
package com.miko.moviedb.presentation.model.detail

data class DetailModel(
    val id: Int,
    val title: String,
    val year: Int,
    val genres: ArrayList<String>,
    val isFavorite: Boolean,
    val synopsis: String,
    val casts: ArrayList<ItemCastModel>
)

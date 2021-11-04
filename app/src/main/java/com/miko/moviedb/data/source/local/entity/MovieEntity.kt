package com.miko.moviedb.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    val id: Int,
    val title: String,
    val duration: Int,
    val year: String,
    val genres: String,
    val isFavorite: Boolean,
    val synopsis: String,
    val poster: String,
    val backDrop: String
)

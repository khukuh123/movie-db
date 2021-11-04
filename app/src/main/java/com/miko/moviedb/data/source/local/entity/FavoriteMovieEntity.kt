package com.miko.moviedb.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "favorite")
data class FavoriteMovieEntity(
    @PrimaryKey
    @NotNull
    val movieId: Int
)

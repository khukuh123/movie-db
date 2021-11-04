package com.miko.moviedb.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithCasts(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
        entity = CastEntity::class
    )
    val casts: List<CastEntity>
)

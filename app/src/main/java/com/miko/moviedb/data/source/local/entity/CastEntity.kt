package com.miko.moviedb.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity

@Entity(tableName = "cast", primaryKeys = ["id", "movieId"])
data class CastEntity(
    @NonNull
    val id: Int,
    val movieId: Int,
    val name: String,
    val avatar: String
)

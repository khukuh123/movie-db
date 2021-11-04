package com.miko.moviedb.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miko.moviedb.data.source.local.entity.CastEntity
import com.miko.moviedb.data.source.local.entity.FavoriteMovieEntity
import com.miko.moviedb.data.source.local.entity.MovieEntity
import com.miko.moviedb.ext.utils.Converters

@Database(entities = [MovieEntity::class, CastEntity::class, FavoriteMovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
package com.miko.moviedb.data.source.local

import com.miko.moviedb.data.source.local.entity.FavoriteMovieEntity
import com.miko.moviedb.data.source.local.entity.MovieWithCasts
import com.miko.moviedb.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {
    fun searchMovies(query: String): Flow<List<MovieWithCasts>> = movieDao.searchMovies(query)

    fun searchFavoriteMovies(query: String): Flow<List<MovieWithCasts>> =
        movieDao.searchFavoriteMovies(query)

    fun getFavoriteMovies(): Flow<List<MovieWithCasts>> = movieDao.getFavoriteMovies()

    fun updateMovieFavorite(added: Boolean, id: Int) = movieDao.updateMovieFavorite(added, FavoriteMovieEntity(id))

    fun insertMovies(movieEntities: List<MovieWithCasts>) = movieDao.insertMoviesWithCasts(movieEntities)

    fun checkFavoriteStatus(id: Int): Flow<List<FavoriteMovieEntity>> =
        movieDao.checkFavoriteStatus(id)
}
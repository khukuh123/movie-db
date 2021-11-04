package com.miko.moviedb.domain.usecase

import com.miko.moviedb.data.Resource
import com.miko.moviedb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getBanner(): Flow<Resource<out ArrayList<Movie>>>

    fun getPopularMovies(): Flow<Resource<out ArrayList<Movie>>>

    fun getComingSoon(year: Int): Flow<Resource<out ArrayList<Movie>>>

    fun getDetailMovie(id: Int): Flow<Resource<out Movie>>

    fun searchMovies(query: String): Flow<ArrayList<Movie>>

    fun searchFavoriteMovies(query: String): Flow<ArrayList<Movie>>

    fun getFavoriteMovies(): Flow<ArrayList<Movie>>

    fun updateMovieFavorite(added: Boolean, id: Int)

    fun checkFavoriteStatus(id: Int): Flow<ArrayList<Movie>>
}
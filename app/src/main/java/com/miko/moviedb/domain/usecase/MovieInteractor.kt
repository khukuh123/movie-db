package com.miko.moviedb.domain.usecase

import com.miko.moviedb.data.MovieRepository
import com.miko.moviedb.data.Resource
import com.miko.moviedb.domain.model.Movie
import com.miko.moviedb.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {
    override fun getBanner(): Flow<Resource<out ArrayList<Movie>>> =
        movieRepository.getBanner()

    override fun getPopularMovies(): Flow<Resource<out ArrayList<Movie>>> =
        movieRepository.getPopularMovies()

    override fun getComingSoon(year: Int): Flow<Resource<out ArrayList<Movie>>> =
        movieRepository.getComingSoon(year)

    override fun getDetailMovie(id: Int): Flow<Resource<out Movie>> =
        movieRepository.getDetailMovie(id)

    override fun searchMovies(query: String): Flow<ArrayList<Movie>> =
        movieRepository.searchMovies(query)

    override fun searchFavoriteMovies(query: String): Flow<ArrayList<Movie>> =
        movieRepository.searchFavoriteMovies(query)

    override fun getFavoriteMovies(): Flow<ArrayList<Movie>> =
        movieRepository.getFavoriteMovies()

    override fun updateMovieFavorite(added: Boolean, id: Int) =
        movieRepository.updateMovieFavorite(added, id)

    override fun checkFavoriteStatus(id: Int): Flow<ArrayList<Movie>> =
        movieRepository.checkFavoriteStatus(id)
}
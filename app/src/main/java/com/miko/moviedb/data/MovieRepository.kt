package com.miko.moviedb.data

import com.miko.moviedb.data.source.local.LocalDataSource
import com.miko.moviedb.data.source.remote.RemoteDataSource
import com.miko.moviedb.data.source.remote.network.ApiResponse
import com.miko.moviedb.domain.model.Movie
import com.miko.moviedb.domain.repository.IMovieRepository
import com.miko.moviedb.ext.utils.AppExecutors
import com.miko.moviedb.ext.utils.Mapper.toMovie
import com.miko.moviedb.ext.utils.Mapper.toMovieEntities
import com.miko.moviedb.ext.utils.Mapper.toMovieEntity
import com.miko.moviedb.ext.utils.Mapper.toMovies
import com.miko.moviedb.ext.utils.Mapper.toMoviesF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {
    override fun getBanner(): Flow<Resource<out ArrayList<Movie>>> =
        flow {
            emit(Resource.Loading(null))

            when (val apiResponse = remoteDataSource.getBanner().first()) {
                is ApiResponse.Success -> {
                    appExecutors.diskIO().execute {
                        localDataSource.insertMovies(apiResponse.data.toMovieEntities().take(3))
                    }

                    val result = ArrayList(apiResponse.data.toMovies().take(3))
                    emit(Resource.Success(result))
                }

                is ApiResponse.Error -> {
                    emit(Resource.Error(null, apiResponse.errorMessage))
                }

                is ApiResponse.Empty -> {
                    emit(Resource.Error(arrayListOf(), "Empty"))
                }
            }
        }.flowOn(Dispatchers.Main)

    override fun getPopularMovies(): Flow<Resource<out ArrayList<Movie>>> =
        flow {
            emit(Resource.Loading(null))

            when (val apiResponse = remoteDataSource.getPopularMovies().first()) {
                is ApiResponse.Success -> {
                    appExecutors.diskIO().execute {
                        localDataSource.insertMovies(apiResponse.data.toMovieEntities())
                    }

                    emit(Resource.Success(apiResponse.data.toMovies()))
                }

                is ApiResponse.Error -> {
                    emit(Resource.Error(null, apiResponse.errorMessage))
                }

                is ApiResponse.Empty -> {
                    emit(Resource.Error(arrayListOf(), "Empty"))
                }
            }
        }.flowOn(Dispatchers.Main)

    override fun getComingSoon(year: Int): Flow<Resource<out ArrayList<Movie>>> =
        flow {
            emit(Resource.Loading(null))

            when (val apiResponse = remoteDataSource.getComingSoon(year).first()) {
                is ApiResponse.Success -> {
                    appExecutors.diskIO().execute {
                        localDataSource.insertMovies(apiResponse.data.toMovieEntities())
                    }
                    emit(Resource.Success(apiResponse.data.toMovies()))
                }

                is ApiResponse.Error -> {
                    emit(Resource.Error(null, apiResponse.errorMessage))
                }

                is ApiResponse.Empty -> {
                    emit(Resource.Error(arrayListOf(), "Empty"))
                }
            }
        }.flowOn(Dispatchers.Main)

    override fun getDetailMovie(id: Int): Flow<Resource<out Movie>> =
        flow {
            emit(Resource.Loading(null))

            when (val apiResponse = remoteDataSource.getDetailMovie(id).first()) {
                is ApiResponse.Success -> {
                    appExecutors.diskIO().execute {
                        localDataSource.insertMovies(listOf(apiResponse.data.toMovieEntity()))
                    }

                    emit(Resource.Success(apiResponse.data.toMovie()))
                }

                is ApiResponse.Error -> {
                    emit(Resource.Error(null, apiResponse.errorMessage))
                }

                is ApiResponse.Empty -> {
                    emit(Resource.Error(null, "Empty"))
                }
            }
        }.flowOn(Dispatchers.Main)

    override fun searchMovies(query: String): Flow<ArrayList<Movie>> =
        localDataSource.searchMovies(query).map {
            it.toMovies()
        }.take(1)

    override fun searchFavoriteMovies(query: String): Flow<ArrayList<Movie>> =
        localDataSource.searchFavoriteMovies(query).map {
            it.toMovies()
        }.take(1)

    override fun getFavoriteMovies(): Flow<ArrayList<Movie>> =
        localDataSource.getFavoriteMovies().map {
            it.toMovies()
        }.take(1)

    override fun updateMovieFavorite(added: Boolean, id: Int) {
        appExecutors.diskIO().execute {
            localDataSource.updateMovieFavorite(added, id)
        }
    }

    override fun checkFavoriteStatus(id: Int): Flow<ArrayList<Movie>> =
        localDataSource.checkFavoriteStatus(id).map {
            it.toMoviesF()
        }.take(1)
}
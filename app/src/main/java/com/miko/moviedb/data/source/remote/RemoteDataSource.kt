package com.miko.moviedb.data.source.remote

import android.util.Log
import com.miko.moviedb.data.source.remote.network.ApiResponse
import com.miko.moviedb.data.source.remote.network.ApiService
import com.miko.moviedb.data.source.remote.response.DetailMovieResponse
import com.miko.moviedb.data.source.remote.response.GenreModel
import com.miko.moviedb.data.source.remote.response.ListMovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.HttpException

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getBanner(): Flow<ApiResponse<ListMovieResponse>> =
        flow {
            try {
                val response = apiService.getBanner()
                val response2 = apiService.getGenres()
                response.itemMovies.forEach{ itemMovie ->
                    val hashMap = HashMap<Int, String>()
                    response2.genres.forEach { genre ->
                        hashMap[genre.id] = genre.name
                    }
                    val resultGenres = ArrayList<GenreModel>()
                    itemMovie.genreIds.forEach { id ->
                        resultGenres.add(GenreModel(id, hashMap[id]!!))
                    }
                    itemMovie.genres = resultGenres
                }

                emit(ApiResponse.Success(response))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> emit(ApiResponse.Error("Network Error\nMake sure you're connected to the internet"))

                    is HttpException -> emit(ApiResponse.Error("${t.code()}\n${t.message()}"))

                    else -> {
                        emit(ApiResponse.Error("Unknown Error\n${t.message.toString()}"))
                        Log.d("RemoteDataSource", t.message.toString())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getPopularMovies(): Flow<ApiResponse<ListMovieResponse>> =
        flow {
            try {
                val response = apiService.getPopularMovies()
                val response2 = apiService.getGenres()
                response.itemMovies.forEach{ itemMovie ->
                    val hashMap = HashMap<Int, String>()
                    response2.genres.forEach { genre ->
                        hashMap[genre.id] = genre.name
                    }
                    val resultGenres = ArrayList<GenreModel>()
                    itemMovie.genreIds.forEach { id ->
                        resultGenres.add(GenreModel(id, hashMap[id]!!))
                    }
                    itemMovie.genres = resultGenres
                }

                emit(ApiResponse.Success(response))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> emit(ApiResponse.Error("Network Error\nMake sure you're connected to the internet"))

                    is HttpException -> emit(ApiResponse.Error("${t.code()}\n${t.message()}"))

                    else -> {
                        emit(ApiResponse.Error("Unknown Error\n${t.message.toString()}"))
                        Log.d("RemoteDataSource", t.message.toString())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getComingSoon(year: Int): Flow<ApiResponse<ListMovieResponse>> =
        flow {
            try {
                val response = apiService.getComingSoon(year)
                val response2 = apiService.getGenres()
                response.itemMovies.forEach{ itemMovie ->
                    val hashMap = HashMap<Int, String>()
                    response2.genres.forEach { genre ->
                        hashMap[genre.id] = genre.name
                    }
                    val resultGenres = ArrayList<GenreModel>()
                    itemMovie.genreIds.forEach { id ->
                        resultGenres.add(GenreModel(id, hashMap[id]!!))
                    }
                    itemMovie.genres = resultGenres
                }

                emit(ApiResponse.Success(response))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> emit(ApiResponse.Error("Network Error\nMake sure you're connected to the internet"))

                    is HttpException -> emit(ApiResponse.Error("${t.code()}\n${t.message()}"))

                    else -> {
                        emit(ApiResponse.Error("Unknown Error\n${t.message.toString()}"))
                        Log.d("RemoteDataSource", t.message.toString())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailMovie(id: Int): Flow<ApiResponse<DetailMovieResponse>> =
        flow {
            try {
                val response = apiService.getDetailMovie(id)
                val response2 = apiService.getCast(id)
                response.casts = response2.cast

                emit(ApiResponse.Success(response))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> emit(ApiResponse.Error("Network Error\nMake sure you're connected to the internet"))

                    is HttpException -> emit(ApiResponse.Error("${t.code()}\n${t.message()}"))

                    else -> {
                        emit(ApiResponse.Error("Unknown Error\n${t.message.toString()}"))
                        Log.d("RemoteDataSource", t.message.toString())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
}
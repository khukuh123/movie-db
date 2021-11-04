package com.miko.moviedb.data.source.remote.network

import com.miko.moviedb.BuildConfig
import com.miko.moviedb.data.source.remote.response.CastResponse
import com.miko.moviedb.data.source.remote.response.DetailMovieResponse
import com.miko.moviedb.data.source.remote.response.GenreResponse
import com.miko.moviedb.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    suspend fun getBanner(): ListMovieResponse

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    suspend fun getPopularMovies(): ListMovieResponse

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    suspend fun getComingSoon(@Query("year") year: Int): ListMovieResponse

    @GET("movie/{id}?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getDetailMovie(@Path("id") id: Int): DetailMovieResponse

    @GET("genre/movie/list?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getGenres(): GenreResponse

    @GET("movie/{id}/credits?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getCast(@Path("id") id: Int): CastResponse
}
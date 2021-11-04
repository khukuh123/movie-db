package com.miko.moviedb.ext.utils

import com.miko.moviedb.data.source.local.entity.CastEntity
import com.miko.moviedb.data.source.local.entity.FavoriteMovieEntity
import com.miko.moviedb.data.source.local.entity.MovieEntity
import com.miko.moviedb.data.source.local.entity.MovieWithCasts
import com.miko.moviedb.data.source.remote.response.DetailMovieResponse
import com.miko.moviedb.data.source.remote.response.ListMovieResponse
import com.miko.moviedb.domain.model.Movie
import com.miko.moviedb.presentation.model.detail.DetailModel
import com.miko.moviedb.presentation.model.detail.ItemCastModel
import com.miko.moviedb.presentation.model.favorite.ItemFavoriteModel
import com.miko.moviedb.presentation.model.home.BannerModel
import com.miko.moviedb.presentation.model.home.ItemHomeModel
import com.miko.moviedb.presentation.model.popular.ItemPopularModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Mapper {

    fun generateLists(): ArrayList<Movie> {
        val result = ArrayList<Movie>()

        for (i in 0 until 5) {
            result.add(
                Movie(
                    i,
                    "Movie $i",
                    i,
                    "202$i",
                    ArrayList(Array(5) {
                        "Genre $it"
                    }.toList()),
                    false,
                    "ini Synopsis",
                    ArrayList(Array(5) {
                        ItemCastModel("Orang $it", "$i")
                    }.toList()),
                    "$i",
                    "$i"
                )
            )
        }

        return result
    }

    //Domain to presentation
    fun Movie.toDetailModel() =
        DetailModel(
            id, title, duration, genres, isFavorite, synopsis, casts, backDrop
        )

    private fun Movie.toItemFavoriteModel() =
        ItemFavoriteModel(
            id, title, year, genres, backDrop, isFavorite
        )

    private fun Movie.toBannerModel() =
        BannerModel(
            id, backDrop
        )

    private fun Movie.toItemHomeModel() =
        ItemHomeModel(
            id, poster
        )

    private fun Movie.toItemPopularModel() =
        ItemPopularModel(
            id, title, genres.first(), synopsis, poster
        )

    fun ArrayList<Movie>.toBannerModels(): ArrayList<BannerModel> =
        ArrayList(map { it.toBannerModel() })

    fun ArrayList<Movie>.toItemHomeModels(): ArrayList<ItemHomeModel> =
        ArrayList(map { it.toItemHomeModel() })

    fun ArrayList<Movie>.toItemPopularModels(): ArrayList<ItemPopularModel> =
        ArrayList(map { it.toItemPopularModel() })

    fun ArrayList<Movie>.toItemFavoriteModels(): ArrayList<ItemFavoriteModel> =
        ArrayList(map { it.toItemFavoriteModel() })

    //Remote to Domain
    private fun ListMovieResponse.ItemMovie.toMovie(): Movie {
        val releaseYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(releaseDate) ?: Date()
        )

        return Movie(
            id,
            title,
            -1,
            releaseYear,
            ArrayList(genres.map { it.name }),
            false,
            overview,
            arrayListOf(),
            posterPath ?: "",
            backdropPath ?: ""
        )
    }

    fun ListMovieResponse.toMovies(): ArrayList<Movie> =
        ArrayList(itemMovies.map { it.toMovie() })

    fun DetailMovieResponse.toMovie(): Movie {
        val releaseYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(releaseDate) ?: Date()
        )

        return Movie(
            id,
            title,
            runtime,
            releaseYear,
            ArrayList(genres.map { it.name }),
            false,
            overview,
            ArrayList(casts.map { ItemCastModel(it.name, it.profilePath ?: "") }),
            posterPath,
            backdropPath
        )
    }

    //Local to Domain
    fun MovieWithCasts.toMovie() =
        Movie(
            movie.id,
            movie.title,
            movie.duration,
            movie.year,
            ArrayList(movie.genres.split(',')),
            movie.isFavorite,
            movie.synopsis,
            ArrayList(casts.map {
                ItemCastModel(it.name, it.avatar)
            }),
            movie.poster,
            movie.backDrop
        )

    fun List<MovieWithCasts>.toMovies(): ArrayList<Movie> =
        ArrayList(map { it.toMovie() })

    fun FavoriteMovieEntity.toMovie() =
        Movie(
            movieId,
            "",
            -1,
            "-1",
            arrayListOf(),
            false,
            "",
            arrayListOf(),
            "",
            ""
        )

    fun List<FavoriteMovieEntity>.toMoviesF(): ArrayList<Movie> =
        ArrayList(map { it.toMovie() })


    //Remote to Local
    private fun ListMovieResponse.ItemMovie.toMovieEntity(): MovieWithCasts {
        val releaseYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(releaseDate) ?: Date()
        )

        return MovieWithCasts(
            MovieEntity(
                id,
                title,
                -1,
                releaseYear,
                genres.map { it.name }.joinToString(),
                false,
                overview,
                posterPath ?: "" ,
                backdropPath ?: ""
            ),
            arrayListOf()
        )
    }
    fun ListMovieResponse.toMovieEntities(): List<MovieWithCasts> =
        ArrayList(itemMovies.map { it.toMovieEntity() })

    fun DetailMovieResponse.toMovieEntity(): MovieWithCasts {
        val releaseYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(releaseDate) ?: Date()
        )

        return MovieWithCasts(
            MovieEntity(
                id,
                title,
                runtime,
                releaseYear,
                genres.map { it.name }.joinToString(),
                false,
                overview,
                posterPath,
                backdropPath
            ),
            casts.map {
                CastEntity(
                    it.id, id, it.name, it.profilePath ?: ""
                )
            }
        )
    }
}
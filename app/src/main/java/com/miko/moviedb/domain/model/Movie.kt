package com.miko.moviedb.domain.model

import com.miko.moviedb.presentation.model.detail.DetailModel
import com.miko.moviedb.presentation.model.detail.ItemCastModel
import com.miko.moviedb.presentation.model.favorite.ItemFavoriteModel
import com.miko.moviedb.presentation.model.home.BannerModel
import com.miko.moviedb.presentation.model.home.ItemHomeModel
import com.miko.moviedb.presentation.model.popular.ItemPopularModel

data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val genres: ArrayList<String>,
    val isFavorite: Boolean,
    val synopsis: String,
    val casts: ArrayList<ItemCastModel>,
    val poster: String
) {

    companion object{
        fun generateLists(): ArrayList<Movie>{
            val result = ArrayList<Movie>()

            for(i in 0 until 5){
                result.add(
                    Movie(
                        i,
                        "Movie $i",
                        "202$i".toInt(),
                        ArrayList(Array(5){
                            "Genre $it"
                        }.toList()),
                        false,
                        "ini Synopsis",
                        ArrayList(Array(5){
                            ItemCastModel("Orang $it", "$i")
                        }.toList()),
                        "$i"
                    )
                )
            }

            return result
        }
    }

    fun toDetailModel() =
        DetailModel(
            id, title, year, genres, isFavorite, synopsis, casts
        )

    fun toItemFavoriteModel() =
        ItemFavoriteModel(
            id, title, year, genres, poster, isFavorite
        )

    fun toBannerModel() =
        BannerModel(
            id, poster
        )

    fun toItemHomeModel() =
        ItemHomeModel(
            id, poster
        )

    fun toItemPopularModel() =
        ItemPopularModel(
            id, title, genres[0], ArrayList(casts.map { it.name }), poster
        )
}
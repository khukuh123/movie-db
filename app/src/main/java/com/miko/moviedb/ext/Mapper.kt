package com.miko.moviedb.ext

import com.miko.moviedb.domain.model.Movie
import com.miko.moviedb.presentation.model.favorite.ItemFavoriteModel
import com.miko.moviedb.presentation.model.home.BannerModel
import com.miko.moviedb.presentation.model.home.ItemHomeModel
import com.miko.moviedb.presentation.model.popular.ItemPopularModel

object Mapper {
    fun ArrayList<Movie>.toBannerModels(): ArrayList<BannerModel> =
        ArrayList(map { it.toBannerModel() })

    fun ArrayList<Movie>.toItemHomeModels(): ArrayList<ItemHomeModel> =
        ArrayList(map { it.toItemHomeModel() })

    fun ArrayList<Movie>.toItemPopularModels(): ArrayList<ItemPopularModel> =
        ArrayList(map { it.toItemPopularModel() })

    fun ArrayList<Movie>.toItemFavoriteModels(): ArrayList<ItemFavoriteModel> =
        ArrayList(map { it.toItemFavoriteModel() })
}